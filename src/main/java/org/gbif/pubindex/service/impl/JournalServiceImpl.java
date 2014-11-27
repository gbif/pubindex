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

import org.gbif.pubindex.model.Journal;
import org.gbif.pubindex.rome.modules.prism.PrismModule;
import org.gbif.pubindex.service.JournalService;
import org.gbif.pubindex.service.mapper.JournalMapper;
import org.gbif.pubindex.util.ErrorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;

import com.google.inject.Inject;
import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JournalServiceImpl implements JournalService {
  private static Logger LOG = LoggerFactory.getLogger(JournalServiceImpl.class);
  private final HttpClient client;
  private final JournalMapper mapper;

  @Inject
  public JournalServiceImpl(HttpClient client, JournalMapper mapper) {
    this.client=client;
    this.mapper = mapper;
  }

  @Override
  public SyndFeed readFeed(Journal j) {
    SyndFeed feed=null;
    if (j==null) return null;

    LOG.debug("Reading latest rss feed for journal {}", j.getId());
    if (StringUtils.isBlank(j.getFeedUrl())){
      LOG.warn("Journal {} has no rss feed configured", j.getId());
      return null;
    }
    InputStream stream=null;
    try {
      // use http client to handle redirects and network failures gracefully
      HttpGet get = new HttpGet(j.getFeedUrl());
      // http header
      get.addHeader("User-Agent", "GBIF-PubIndex/1.0");

      HttpResponse response = client.execute(get);
      LOG.debug("Feed http status: {} {}", response.getStatusLine().getStatusCode(),
        response.getStatusLine().getReasonPhrase());
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        stream = entity.getContent();
        SyndFeedInput input = new SyndFeedInput();
        feed = input.build(new XmlReader(stream, entity.getContentType().getValue()));
        j.setError(null);
        LOG.info("Current feed for journal " + j.getId() + " published on {} contains {} articles",
          feed.getPublishedDate(), feed.getEntries().size());
        // update journal, find new properties we dont yet have
        updateJournalFromFeed(j,feed);
      }else{
        LOG.warn("Journal {} feed {} has no content", j.getId(), j.getFeedUrl());
        j.setError("Journal feed has no content");
      }
    } catch (MalformedURLException e) {
      LOG.warn("Journal {} has invalid feed URL {}", j.getId(), j.getFeedUrl());
      j.setError(ErrorUtils.getErrorMessage(e));
    } catch (FeedException e) {
      LOG.warn("Feed for journal {} cannot be parsed {}", j.getId(), e.getMessage());
      j.setError(ErrorUtils.getErrorMessage(e));
    } catch (IOException e) {
      LOG.warn("Cant read feed for journal {} : {}", j.getId(), e.getMessage());
      j.setError(ErrorUtils.getErrorMessage(e));
    } catch (Exception e) {
      LOG.warn("Unkown exception while reading feed for journal {} : {}", j.getId(), e.getMessage());
      j.setError(ErrorUtils.getErrorMessage(e));
    } finally {
      if (stream!=null){
        try {
          stream.close();
        } catch (IOException e) {
        }
      }
    }

    // mark as harvested
    j.setLastHarvest(new Date());
    // update postgres
    mapper.update(j);

    return feed;
  }

  private void updateJournalFromFeed(Journal j, SyndFeed feed){
    if (j.getTitle()==null){
      j.setTitle(feed.getTitle());
    }
    if (j.getHomepage()==null){
      j.setHomepage(feed.getLink());
    }
    if (j.getDescription()==null){
      j.setDescription(feed.getDescription());
    }
    if (j.getKeywords()==null){
      j.setHomepage(FeedParserRome.concatCategories(feed.getCategories()));
    }
    DCModule dc = (DCModule) feed.getModule(DCModule.URI);
    if (dc!=null){
      if (j.getPublisher()==null){
        j.setPublisher(dc.getPublisher());
      }
      if (j.getLanguage()==null){
        j.setLanguage(dc.getLanguage());
      }
    }
    PrismModule prism = (PrismModule) feed.getModule(PrismModule.URI);
    if (prism!=null){
      if (j.getIssn()==null){
        j.setIssn(prism.getIssn());
      }
      if (j.getIssnElectronic()==null){
        j.setIssnElectronic(prism.getEIssn());
      }
    }
  }

  @Override
  public Journal getByFeed(String url) {
    return mapper.getByFeed(url);
  }

  @Override
  public Journal getNextJournalForHarvesting() {
    return mapper.getNextJournalForHarvesting();
  }
}
