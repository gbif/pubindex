/*
 * Copyright 2011 Global Biodiversity Information Facility (GBIF)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.pubindex.manager.impl;

import org.gbif.pubindex.manager.ArticleIndexer;
import org.gbif.pubindex.manager.ArticleManager;
import org.gbif.pubindex.manager.NameFoundManager;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.NameFound;
import org.gbif.utils.HttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class ArticleIndexerImpl implements ArticleIndexer {

  private Pattern cleanTFName = Pattern.compile("\\[(.+)\\]");
  private String finderWS = "http://ecat-dev.gbif.org/tf";
  private Logger log = LoggerFactory.getLogger(getClass());
  private ArticleManager articleManager;
  private NameFoundManager nameFoundManager;
  private HttpUtil http;
  private DefaultHttpClient client;
  private ObjectMapper mapper = new ObjectMapper();
  private JsonFactory jsonFactory = new JsonFactory();

  @Inject
  public ArticleIndexerImpl(ArticleManager articleManager, HttpUtil http, DefaultHttpClient client,
    NameFoundManager nameFoundManager) {
    this.articleManager = articleManager;
    this.nameFoundManager = nameFoundManager;
    this.http = http;
    this.client = client;
  }

  /**
   * Scans an article for scientific names using a namefinding webservice.
   * This includes downloading any missing files like pdfs linked from the article.
   */
  @Override
  public List<NameFound> index(Article article) {
    if (article == null) return new ArrayList<NameFound>();

    // download linked document and extract text
    grabText(article);
    // find names
    List<NameFound> names = findNames(article);
    // persist names
    log.debug("persist {} found names for article {}", names.size(), article.getId());
    nameFoundManager.replaceNameInArticle(article.getId(), names);
    // mark as indexed
    log.debug("update article {} as indexed", article.getId());
    article.setLastIndexed(new Date());
    articleManager.update(article);

    return names;
  }

  private void grabText(Article article) {
    File storedFile = articleManager.getArticleFile(article);
    if (storedFile.exists()) {
      log.debug("Article {} already downloaded: {}", article.getId(), storedFile.getAbsolutePath());
    } else if (StringUtils.isBlank(article.getUrl())) {
      log.debug("Article {} has no link, find names in feed data only", article.getId());
    } else {
      // only download if its never been downloaded before
      try {
        article.setUrlContentType(headContentType(article.getUrl()));
        log.debug("Download article from {} to {}", article.getUrl(), storedFile.getAbsolutePath());
        StatusLine status = http.download(article.getUrlAsURL(), storedFile);
        if (HttpUtil.success(status)) {
          log.debug("Successfully downloaded article {} to {}", article.getId(), storedFile.getAbsolutePath());
          article.setError(null);
        } else {
          log.debug("Failed to download linked article {} HTTP{}", article.getId(), status.getStatusCode());
          FileUtils.deleteQuietly(storedFile);
          article.setError("Download failed: HTTP" + status.getStatusCode());
        }
      } catch (Exception e) {
        log.warn("Failed to download linked article {}", article.getId(), e);
        FileUtils.deleteQuietly(storedFile);
        article.setError(JournalManagerImpl.getErrorMessage(e));
      }
    }

    // now find names on existing or newly downloaded file
    if (storedFile.exists()) {
      article.setExtractedText(extractText(article, storedFile));
    }

  }

  private String extractText(Article article, File f) {
    if (article == null | f == null) {
      log.warn("Null arguments not allowed when extracting text for an article");
      return null;
    }

    String text = null;
    FileInputStream in = null;
    try {
      in = new FileInputStream(f);
      InputSource is = new InputSource(in);
      text = ArticleExtractor.INSTANCE.getText(is);
    } catch (FileNotFoundException e) {
      log.error("Article file not found: {}", f.getAbsolutePath());
      article.setError("File not found: " + f.getAbsolutePath());
    } catch (BoilerpipeProcessingException e) {
      log.warn("Cannot extract text with boilerpipe: {}", e.getMessage());
      article.setError("Cannot extract text with boilerpipe: " + e.getMessage());
      // TODO: try with TIKA to extract from pdfs etc...
    } catch (Exception e) {
      log.warn("Cannot extract text: {}", e.getMessage());
      article.setError("Cannot extract text: " + e.getMessage());
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          log.error("Can't close input stream for file {}", f.getAbsoluteFile());
        }
      }
    }
    return text;
  }

  private String headContentType(String url) {
    // execute head request
    HttpHead req = new HttpHead(url);
    try {
      HttpResponse response = client.execute(req);
      Header ct = response.getFirstHeader("Content-Type");
      return ct == null ? null : StringUtils.trimToNull(ct.getValue());
    } catch (Exception e) {
      log.warn("Head request for article {} failed: {}", url, e.getMessage());
    }
    return null;
  }

  private void addArticleName(List<NameFound> names, NameFound newName, Article article, NameFound.Source source) {
    if (newName == null) return;
    newName.setArticleId(article.getId());
    newName.setSource(source);
    names.add(newName);
  }

  private List<NameFound> findNames(Article article) {
    List<NameFound> names = new ArrayList<NameFound>();
    // find in title
    findNamesInText(names, article.getTitle(), NameFound.Source.TITLE, article);
    // find in abstract
    findNamesInText(names, article.getDescription(), NameFound.Source.ABSTRACT, article);
    // find in keywords
    findNamesInText(names, article.getKeywords(), NameFound.Source.KEYWORDS, article);
    // find in full article
    findNamesInText(names, article.getExtractedText(), NameFound.Source.ARTICLE, article);

    log.info("Found {} names in article {}", names.size(), article.getId());
    return names;
  }

  private void findNamesInText(List<NameFound> names, String text, NameFound.Source source, Article article) {
    if (StringUtils.isBlank(text)) {
      log.debug("No text in {}", source);
    } else {
      // call taxon finder service
      Map<String, String> params = new HashMap<String, String>();
      params.put("input", text);
      params.put("type", "text");
      params.put("format", "json");
      try {
        HttpUtil.Response resp = http.post(finderWS, HttpUtil.map2Entity(params));
        log.debug("Names found in {} : {}", source, resp.content);

        Map<String, Object> json = mapper.readValue(resp.content, Map.class);
        List<Map<String, ?>> jsonNames = (List<Map<String, ?>>) json.get("names");
        for (Map<String, ?> n : jsonNames) {
          String sciname = StringUtils.trimToNull((String) n.get("scientificName"));
          if (sciname != null) {
            NameFound name = new NameFound();
            name.setArticleId(article.getId());
            name.setSource(source);
            name.setName(cleanTfArtifacts(sciname));
            name.setNameVerbatim(StringUtils.trimToNull((String) n.get("verbatim")));
            name.setNovum(false);
            // require at least binomials - we'll use a lower confidence for monomials but still store them
            if (sciname.contains(" ")) {
              name.setConfidence(0);
            } else {
              name.setConfidence(1);
            }
            name.setOffsetStart((Integer) n.get("offsetStart"));
            name.setOffsetEnd((Integer) n.get("offsetEnd"));
            names.add(name);
          }
        }

      } catch (URISyntaxException e) {
        // wont happen :)
      } catch (Exception e) {
        log.error("Problem calling name finder: {}", e);
      }
    }
  }

  /**
   * Removes the [] bracket artifacts TaxonFinder inserts when expanding abbreviated species names.
   * For example A.alba becomes A[bies] alba in TF and the brackets should be removed.
   */
  protected String cleanTfArtifacts(String sciname) {
    return cleanTFName.matcher(sciname).replaceAll("$1");
  }

}
