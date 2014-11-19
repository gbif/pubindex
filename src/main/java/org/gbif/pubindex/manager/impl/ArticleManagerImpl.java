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

/**
 *
 */
package org.gbif.pubindex.manager.impl;

import org.gbif.pubindex.manager.ArticleManager;
import org.gbif.pubindex.model.Article;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 */
public class ArticleManagerImpl extends BaseManager<Article> implements ArticleManager {
    private File repo;

@Inject
public ArticleManagerImpl() {
    super("Article");
  }

  @Override
  public void setRepo(File repo) {
    log.info("Using article repository: {}", repo.getAbsolutePath());
    this.repo = repo;
  }

  @Override
    public File getArticleFile(Article article) {
        return new File(repo, "articles/j"+article.getJournalId()+"/a"+article.getId());
    }

    @Override
  public List<Article> listNotYetIndexed() {
    return list(Article.class,"listNotYetIndexed");
  }

  @Override
  public Article getByUrl(String url) {
    return selectOne(Article.class,"getByUrl",url);
  }

  /**
   * Takes a list of transient, potentially new articles and compares them with existing persisted articles.
   * Only truely new articles are then persisted and returned.
   *
   * @param articles without id that might be new
   * @return a new list with only the new, persisted articles
   *
   */
  @Override
  public List<Article> persistNewArticles(List<Article> articles) {
    List<Article> articles2 = new ArrayList<Article>();
    for (Article a : articles){
      if (a==null) continue;
      Article existing = getByGuidAndJournal(a.getGuid(), a.getJournalId());
      if (existing==null){
        try {
          insert(a);
          articles2.add(a);
        } catch (PersistenceException e) {
          log.error("Error persisting new article for journal "+ a.getJournalId()+" : "+a.getUrl(), e);
        }
      }
    }
    log.debug("{} new articles found", articles2.size());
    return articles2;
  }

  private Article getByGuidAndJournal(String guid, int journalId){
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("guid", guid);
    params.put("journalId", journalId);
    return selectOne(Article.class, "getByGuidAndJournal", params);
  }
}
