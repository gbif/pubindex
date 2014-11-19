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
package org.gbif.pubindex.guice;

import org.gbif.pubindex.PubindexCommander;
import org.gbif.pubindex.indexer.ContinousJournalIndexing;
import org.gbif.pubindex.manager.ArticleIndexer;
import org.gbif.pubindex.manager.ArticleManager;
import org.gbif.pubindex.manager.FeedParser;
import org.gbif.pubindex.manager.JournalManager;
import org.gbif.pubindex.manager.NameFoundManager;
import org.gbif.pubindex.manager.impl.ArticleIndexerImpl;
import org.gbif.pubindex.manager.impl.ArticleManagerImpl;
import org.gbif.pubindex.manager.impl.FeedParserRome;
import org.gbif.pubindex.manager.impl.JournalManagerImpl;
import org.gbif.pubindex.manager.impl.NameFoundManagerImpl;
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

public class GuiceConfig implements Module{

  @Override
  public void configure(Binder binder) {
    binder.bind(JournalManager.class).to(JournalManagerImpl.class);
    binder.bind(ArticleManager.class).to(ArticleManagerImpl.class);
    binder.bind(NameFoundManager.class).to(NameFoundManagerImpl.class);
    binder.bind(ArticleIndexer.class).to(ArticleIndexerImpl.class);
    binder.bind(FeedParser.class).to(FeedParserRome.class);

    binder.bind(ContinousJournalIndexing.class).in(Scopes.SINGLETON);
    binder.bind(PubindexCommander.class).in(Scopes.NO_SCOPE);


  }

  @Provides
  @Singleton
  public SqlSessionFactory provideSqlSessionFactory() throws IOException {
    // setup the iBatis ORM
    // this might not be the optimal way of doing this, but basically the session factory is
    // injected into each manager (basemanager) and the config is read once only here
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
