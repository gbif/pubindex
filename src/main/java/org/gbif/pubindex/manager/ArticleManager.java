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
package org.gbif.pubindex.manager;

import org.gbif.pubindex.model.Article;

import java.io.File;
import java.util.List;

public interface ArticleManager extends CRUDManager<Article>{

  /**
   * (Re)sets the location of the article repository of pubindex
   * @param repo
   */
  public void setRepo(File repo);

  /**
   * returns the file to externally store the linked article in
   * @param article
   * @return
   */
    public File getArticleFile(Article article);

    public List<Article> listNotYetIndexed();

  /**
   * The url to the full article should be unique.
   * This method returns the first matching article for a url or null if nothing can be found.
   * @param url
   * @return
   */
    public Article getByUrl(String url);

  /**
   * Takes a list of transient, potentially new articles and compares them with existing persisted articles.
   * Only truely new articles are then persisted and returned.
   *
   * @param articles without id that might be new
   * @return a new list with only the new, persisted articles
   */
    public List<Article> persistNewArticles(List<Article> articles);
}
