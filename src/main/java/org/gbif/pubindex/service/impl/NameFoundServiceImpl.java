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
package org.gbif.pubindex.service.impl;

import org.gbif.pubindex.service.ArticleService;
import org.gbif.pubindex.service.NameFoundService;
import org.gbif.pubindex.model.NameFound;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author mdoering
 *
 */
public class NameFoundServiceImpl extends BaseServiceImpl<NameFound> implements NameFoundService {

    private ArticleService articleService;

@Inject
public NameFoundServiceImpl(ArticleService articleService) {
    super("NameFound");
    this.articleService = articleService;
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
