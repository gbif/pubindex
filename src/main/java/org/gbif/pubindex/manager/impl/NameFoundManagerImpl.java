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
import org.gbif.pubindex.manager.NameFoundManager;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.NameFound;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author mdoering
 *
 */
public class NameFoundManagerImpl extends BaseManager<NameFound> implements NameFoundManager {

    private ArticleManager articleManager;

@Inject
public NameFoundManagerImpl(ArticleManager articleManager) {
    super("NameFound");
    this.articleManager=articleManager;
  }

  @Override
  public void replaceNameInArticle(int articleID, List<NameFound> names) {
    // remove existing names
    update("deleteByArticle", articleID);
    // insert new ones
    for (NameFound n : names){
      n.setArticleId(articleID);
      insert(n);
    }
  }

  /** @return the list of found names in the given article if index already, otherwise an empty list */
  @Override
  public List<NameFound> listNamesInArticle(int articleID) {
    // TODO: Write implementation
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
