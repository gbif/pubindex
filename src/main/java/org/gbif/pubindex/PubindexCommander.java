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
package org.gbif.pubindex;

import org.gbif.pubindex.guice.GuiceConfig;
import org.gbif.pubindex.indexer.ContinousJournalIndexing;
import org.gbif.pubindex.manager.ArticleIndexer;
import org.gbif.pubindex.manager.ArticleManager;
import org.gbif.pubindex.manager.JournalManager;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.Journal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.syndication.feed.synd.SyndFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubindexCommander {
  private Logger log = LoggerFactory.getLogger(PubindexCommander.class);

  @Parameter(names = {"-indexing"}, description = "Starts up a continuous journal feed indexer that sequentially harvests feeds one by one, extracting and indexing its articles. The service can be shut down and revived any time. Multiple instances working on the same database are tolerated.")
  public boolean indexing=false;

  @Parameter(names = {"-indexArticle"}, description = "(Re-)index given articles and quit")
  public List<Integer> indexArticles=new ArrayList<Integer>();

  @Parameter(names = {"-repo"}, description = "The full path to the article repository for pubindex. Defaults to /tmp/pubindex")
  public File repo = new File("/tmp/pubindex");

  private ContinousJournalIndexing service;
  private ArticleIndexer indexer;
  private ArticleManager articleManager;
  private JournalManager journalManager;

  @Inject
  public PubindexCommander(ContinousJournalIndexing continousJournalIndexing, ArticleIndexer articleIndexer, ArticleManager articleManager,
    JournalManager journalManager){
    service= continousJournalIndexing;
    indexer=articleIndexer;
    this.articleManager= articleManager;
    this.journalManager= journalManager;
  }

  public void run(){
    articleManager.setRepo(repo);

    for (Integer artID : indexArticles) {
      Article a = articleManager.get(artID);
      indexer.index(a);
    }
    if (indexing){
      log.info("Starting continous indexing service");
      service.start();
    }
  };


  public static void main (String[] args){
    Injector injector = Guice.createInjector(new GuiceConfig());
    PubindexCommander cmd = injector.getInstance(PubindexCommander.class);
    new JCommander(cmd,args);
    cmd.run();
  }
}
