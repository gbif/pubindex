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

import org.gbif.pubindex.model.NameFound;

import java.util.List;

public interface NameFoundService {
  /**
   * Removes potentially existing names for an article and inserts new found names as given, updating the articleId of all names with the one given explicitly
   * @param articleID of the article the names should be linked to. Overrides any id found in the names itself
   * @param names to be inserted
   */
  public void replaceNameInArticle(int articleID, List<NameFound> names) throws IllegalArgumentException;

  /**
   *
   * @param articleID
   * @return the list of found names in the given article if index already, otherwise an empty list
   */
  public List<NameFound> listNamesInArticle(int articleID);

}
