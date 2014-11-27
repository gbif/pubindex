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
package org.gbif.pubindex.indexer;

import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.Journal;
import org.gbif.pubindex.service.ArticleIndexer;
import org.gbif.pubindex.service.ArticleService;
import org.gbif.pubindex.service.FeedParser;
import org.gbif.pubindex.service.JournalService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.syndication.feed.synd.SyndFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that continously runs harvesting and indexing of names.
 * It will sequentially harvest and index a journal one by one, picking up unfinished indexing or harvesting work next
 * time we stat it up.
 * It will harvest a single journal and update its last_harvest timestamp as soon as possible. Then continue to extract
 * individual articles, download them and run the name finding on each of them. This allows to run several harvesters
 * simultaneously as nearly all work is done on the indexing.
 */
@Singleton
public class ContinousJournalIndexing {
  private static Logger LOG = LoggerFactory.getLogger(ContinousJournalIndexing.class);
  private final JournalService journalService;
  private final ArticleService articleService;
  private final ArticleIndexer indexer;
  private final FeedParser parser;

  @Inject
  public ContinousJournalIndexing(JournalService journalService, ArticleService articleService, ArticleIndexer indexer,
    FeedParser parser) {
    this.journalService = journalService;
    this.articleService = articleService;
    this.indexer = indexer;
    this.parser = parser;
  }

  public void start() {
    // first pick up unfinished article indexing
    indexUnfinishedArticles();

    // now keep on harvesting journal after journal
    // we never leave this loop!
    while (true) {
      Journal j = journalService.getNextJournalForHarvesting();
      LOG.debug("Next journal to be harvested is {}", j == null ? "null" : j.getId());
      long waitingTime = 0;

      if (j == null) {
        // 5 minutes
        waitingTime = 1000L * 60 * 5;
      } else if (j.getLastHarvest() != null && j.getIndexingInterval() != null) {
        // respect minimum indexing intervalls and wait if necessary
        long nextHarvest = new Date().getTime() - j.getLastHarvest().getTime() - 1000L * 60 * j.getIndexingInterval();
        if (nextHarvest < 0) {
          // wait a little. There might be new entries in the db meanwhile, so dont wait til the next indexing is reached
          waitingTime = 1000L * 60 * 5;
        }
      }

      if (waitingTime > 0) {
        try {
          LOG.info("Sleeping for {} ms", waitingTime);
          Thread.sleep(waitingTime);
        } catch (InterruptedException e) {
          LOG.info("Wakeup continous indexer");
        }
      } else if (j != null) {
        index(j);
      }

    }
  }

  private void indexUnfinishedArticles() {
    List<Article> articles = articleService.listNotYetIndexed();
    LOG.info("Indexing {} never indexed articles", articles.size());
    for (Article article : articles) {
      index(article);
    }
    LOG.info("Finished indexing never indexed articles");
  }

  private void index(Article article) {
    try {
      LOG.debug("Start indexing of article {}", article.getId());
      indexer.index(article);
      LOG.debug("Finished indexing of article {}", article.getId());
    } catch (Exception e) {
      // unexpected exception
      LOG.error("Unexpected exception when indexing article {}", article == null ? "null" : article.getId(), e);
    }
  }

  public void index(Journal j) {
    try {
      LOG.debug("Start harvesting of journal {}", j.getId());
      SyndFeed feed = journalService.readFeed(j);
      List<Article> articles = new ArrayList<Article>();
      if (feed != null) {
        // extract articles
        articles = parser.buildArticles(feed, j.getId());
        articles = articleService.persistNewArticles(articles);
      }
      // now index articles which can take a while
      // we do this therefore after updating the journal to avoid the same journal being picked up for harvesting by other services
      for (Article article : articles) {
        index(article);
      }
    } catch (Exception e) {
      // unexpected exception
      LOG.error("Unexpected exception when indexing journal {}", j == null ? "null" : j.getId(), e);
    }
  }
}
