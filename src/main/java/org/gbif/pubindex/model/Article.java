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
package org.gbif.pubindex.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

public class Article implements Identifiable{
  private Pattern REMOVE_NULL = Pattern.compile("\u0000+");

  private Integer id;
  private Integer journalId;
  private String title;
  private String authors;
  private String publishedIn;
  private Date publishedDate;
  private String description;
  private String keywords;
  private String url;
  private String urlContentType;
  private String error;
  private String doi;
  private String isbn;
  private String extractedText;

  private Date lastIndexed;
  private Date created;

  private String guid;

  public Integer getJournalId() {
    return journalId;
  }

  public void setJournalId(Integer journalId) {
    this.journalId = journalId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public URL getUrlAsURL() {
    URL url2 = null;
    try {
      url2 = new URL(url);
    } catch (MalformedURLException e) {
    }
    return url2;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDoi() {
    return doi;
  }

  public void setDoi(String doi) {
    this.doi = doi;
  }

  public Date getLastIndexed() {
    return lastIndexed;
  }

  public void setLastIndexed(Date lastIndexed) {
    this.lastIndexed = lastIndexed;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getExtractedText() {
    return extractedText;
  }

  public void setExtractedText(String extractedText) {
    // remove null character found sometimes in xml/html
    // this causes postgres to choke!
    this.extractedText = REMOVE_NULL.matcher(extractedText).replaceAll("");
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getPublishedIn() {
    return publishedIn;
  }

  public void setPublishedIn(String publishedIn) {
    this.publishedIn = publishedIn;
  }

  public Date getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(Date publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getUrlContentType() {
    return urlContentType;
  }

  public void setUrlContentType(String urlContentType) {
    this.urlContentType = urlContentType;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }
}
