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
package org.gbif.pubindex.service;

import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.NameFound;

import java.util.List;

public interface ArticleIndexer {

  /**
   * Scans an article for scientific names using a namefinding webservice.
   * This includes downloading any missing files like pdfs linked from the article.
   */
  public List<NameFound> index(Article article);
}
