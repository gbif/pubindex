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

import org.gbif.pubindex.manager.FeedParser;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.rome.modules.prism.PrismModule;
import org.gbif.pubindex.util.DoiUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedParserRome implements FeedParser{
  private Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  public FeedParserRome() {
  }

  /**
   * Extracts and persists articles from a given volume feed.
   * This does not include downloading potentially linked article files to the repository yet.
   * The volume will be updated with information found in the feed, for example the publishing date.
   */
  @Override
  public List<Article> buildArticles(SyndFeed feed, Integer journalId) {
    List<Article> articles = new ArrayList<Article>();

    // go thru items to find volume and date published if not set already
    Iterator entryIter = feed.getEntries().iterator();
    while (entryIter.hasNext()) {
      Article a = handleFeedItem((SyndEntry) entryIter.next(), feed.getTitle());
      if (a!=null){
        a.setJournalId(journalId);
        a.setCreated(new Date());
        articles.add(a);
      }
    }

    return articles;
  }

  private Article handleFeedItem(SyndEntry item, String feedTitle) {
    Article a = new Article();

    a.setTitle(StringUtils.trimToNull(item.getTitle()));
    a.setUrl(StringUtils.trimToNull(item.getLink()));
    a.setAuthors(concat(item.getAuthors()));
    a.setKeywords(concatCategories(item.getCategories()));

    a.setPublishedDate(item.getPublishedDate());
    a.setPublishedIn(StringUtils.trimToNull(feedTitle));
    if (item.getDescription()!=null){
      a.setDescription(StringUtils.trimToNull(item.getDescription().getValue()));
    }
    String guid = StringUtils.trimToNull(item.getUri());
    // dont trust BioStor UUIDs as they change all the time
    if (guid==null || guid.startsWith("urn:uuid")){
      // if no guid was given use the link
      guid = StringUtils.trimToNull(item.getLink());
      if (guid == null) {
        // last resort use the hascode for the entire item
        guid = ""+item.hashCode();
      }
    }
    a.setGuid(guid);
    // check if url or guid containts a doi
    a.setDoi(findDOI(guid,a.getUrl()));

    // parse prism module
    PrismModule prism = (PrismModule) item.getModule(PrismModule.URI);
    if (prism!=null){
      if (prism.getDoi()!=null){
        a.setDoi(prism.getDoi());
      }
    }
    return a;
  }

  protected static String concat(List objs){
    StringBuilder keywords = new StringBuilder();
    boolean first=true;
    for (Object cat : objs){
      if (cat!=null){
        if (!first){
          keywords.append("; ");
        }
        keywords.append(cat.toString());
        first = false;
      }
    }
    return StringUtils.trimToNull(keywords.toString());
  }
  protected static String concatCategories(List<SyndCategoryImpl> objs){
    StringBuilder keywords = new StringBuilder();
    boolean first=true;
    for (Object cat : objs){
      if (cat!=null){
        if (!first){
          keywords.append("; ");
        }
        keywords.append(((SyndCategoryImpl) cat).getName());
        first = false;
      }
    }
    return StringUtils.trimToNull(keywords.toString());
  }
  /**
   * http://dx.doi.org/10.1111%2Fj.1654-1103.2011.01343.x
   * doi:10.1111/j.1654-1103.2011.01343.x
   * 10.1111/j.1654-1103.2011.01343.x
   * @param text
   * @return
   */
  private String findDOI(String ... text) {
    String doi = null;
    for (String x : text){
      doi = DoiUtils.extractDoi(x);
      if (doi!=null) break;
    }
    return doi;
  }
}
