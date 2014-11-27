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
package org.gbif.pubindex.config;

import org.gbif.mybatis.guice.MyBatisModule;
import org.gbif.pubindex.indexer.ContinousJournalIndexing;
import org.gbif.pubindex.model.Article;
import org.gbif.pubindex.model.Journal;
import org.gbif.pubindex.model.NameFound;
import org.gbif.pubindex.service.ArticleIndexer;
import org.gbif.pubindex.service.ArticleService;
import org.gbif.pubindex.service.FeedParser;
import org.gbif.pubindex.service.JournalService;
import org.gbif.pubindex.service.NameFoundService;
import org.gbif.pubindex.service.mapper.ArticleMapper;
import org.gbif.pubindex.service.mapper.JournalMapper;
import org.gbif.pubindex.service.mapper.NameFoundMapper;
import org.gbif.pubindex.service.impl.ArticleIndexerImpl;
import org.gbif.pubindex.service.impl.ArticleServiceImpl;
import org.gbif.pubindex.service.impl.FeedParserRome;
import org.gbif.pubindex.service.impl.JournalServiceImpl;
import org.gbif.pubindex.service.impl.NameFoundServiceImpl;
import org.gbif.utils.HttpUtil;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class PubindexMybatisModule extends MyBatisModule {
  private final PubindexConfig cfg;

  public PubindexMybatisModule(PubindexConfig cfg) {
    super(cfg.clb.buildProperties());
    this.cfg = cfg;
  }

  @Override
  protected void bindMappers() {
    addAlias("Journal").to(Journal.class);
    addAlias("Article").to(Article.class);
    addAlias("NameFound").to(NameFound.class);

    addMapperClass(JournalMapper.class);
    addMapperClass(ArticleMapper.class);
    addMapperClass(NameFoundMapper.class);
  }

  @Override
  protected void bindTypeHandlers() {
    // nothing to do
  }

  @Override
  protected void bindManagers() {
    bind(PubindexConfig.class).toInstance(cfg);
    bind(JournalService.class).to(JournalServiceImpl.class).in(Scopes.SINGLETON);
    bind(ArticleService.class).to(ArticleServiceImpl.class).in(Scopes.SINGLETON);
    bind(NameFoundService.class).to(NameFoundServiceImpl.class).in(Scopes.SINGLETON);

    bind(ArticleIndexer.class).to(ArticleIndexerImpl.class);
    bind(FeedParser.class).to(FeedParserRome.class);
    bind(ContinousJournalIndexing.class).in(Scopes.SINGLETON);

  }

  @Provides
  @Singleton
  public HttpClient provideHttpClient(){
    return HttpUtil.newMultithreadedClient(10000, 20, 10);
  }

  @Provides
  @Singleton
  @Inject
  public HttpUtil provideHttpUtil(HttpClient client) {
    return new HttpUtil((DefaultHttpClient) client);
  }

}
