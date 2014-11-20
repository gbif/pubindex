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

import org.gbif.pubindex.indexer.ContinousJournalIndexing;
import org.gbif.pubindex.service.ArticleIndexer;
import org.gbif.pubindex.service.ArticleService;
import org.gbif.pubindex.service.FeedParser;
import org.gbif.pubindex.service.JournalService;
import org.gbif.pubindex.service.NameFoundService;
import org.gbif.pubindex.service.impl.ArticleIndexerImpl;
import org.gbif.pubindex.service.impl.ArticleServiceImpl;
import org.gbif.pubindex.service.impl.FeedParserRome;
import org.gbif.pubindex.service.impl.JournalServiceImpl;
import org.gbif.pubindex.service.impl.NameFoundServiceImpl;
import org.gbif.utils.HttpUtil;

import java.io.IOException;
import java.io.Reader;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class GuiceConfig implements Module {
  private final PubindexConfig cfg;

  public GuiceConfig(PubindexConfig cfg) {
    this.cfg = cfg;
  }

  @Override
  public void configure(Binder binder) {
    binder.bind(PubindexConfig.class).toInstance(cfg);
    binder.bind(JournalService.class).to(JournalServiceImpl.class);
    binder.bind(ArticleService.class).to(ArticleServiceImpl.class);
    binder.bind(NameFoundService.class).to(NameFoundServiceImpl.class);
    binder.bind(ArticleIndexer.class).to(ArticleIndexerImpl.class);
    binder.bind(FeedParser.class).to(FeedParserRome.class);

    binder.bind(ContinousJournalIndexing.class).in(Scopes.SINGLETON);
  }

  @Provides
  @Singleton
  public SqlSessionFactory provideSqlSessionFactory() throws IOException {
    Reader reader = Resources.getResourceAsReader("ibatis-config.xml");
    return new SqlSessionFactoryBuilder().build(reader);
  }

  @Provides
  @Singleton
  public DefaultHttpClient provideHttpClient(){
    return HttpUtil.newMultithreadedClient(10000, 20, 10);
  }

  @Provides
  @Singleton
  @Inject
  public HttpUtil provideHttpUtil(DefaultHttpClient client) {
    return new HttpUtil(client);
  }

}
