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

import java.util.List;

import com.sun.syndication.feed.synd.SyndFeed;

public interface FeedParser {

  /**
   * Extracts articles from a given rss feed without persisting them.
   * This does not include downloading potentially linked article files to the repository yet.
   *
   * @param feed
   * @param journalId
   * @return
   */
  public List<Article> buildArticles(SyndFeed feed, Integer journalId);
}
