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

public class NameFound implements Identifiable {
  public enum Source{
    TITLE,ABSTRACT,KEYWORDS,ARTICLE;
    public static Source lookup(Integer sourceid){
      if (sourceid==null) return null;
      int ord = sourceid;
      for (Source s : Source.values()){
        if (ord==s.ordinal()){
          return s;
        }
      }
      return null;
    }
  };

  private Integer id;
  private Integer articleId;
  private Integer nubId;
  private Source source;
  private String name;
  private String nameVerbatim;
  private boolean novum;
  private Integer offsetStart;
  private Integer offsetEnd;
  private Integer confidence;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNameVerbatim() {
    return nameVerbatim;
  }

  public void setNameVerbatim(String nameVerbatim) {
    this.nameVerbatim = nameVerbatim;
  }

  public boolean isNovum() {
    return novum;
  }

  public void setNovum(boolean novum) {
    this.novum = novum;
  }

  public Integer getOffsetStart() {
    return offsetStart;
  }

  public void setOffsetStart(Integer offsetStart) {
    this.offsetStart = offsetStart;
  }

  public Integer getOffsetEnd() {
    return offsetEnd;
  }

  public void setOffsetEnd(Integer offsetEnd) {
    this.offsetEnd = offsetEnd;
  }

  public Integer getConfidence() {
    return confidence;
  }

  public void setConfidence(Integer confidence) {
    this.confidence = confidence;
  }

  public Integer getNubId() {
    return nubId;
  }

  public void setNubId(Integer nubId) {
    this.nubId = nubId;
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public Integer getSourceAsInt() {
    return source==null ? null : source.ordinal();
  }

  public void setSourceAsInt(Integer sourceid) {
    this.source = Source.lookup(sourceid);
  }

}
