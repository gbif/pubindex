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
package org.gbif.pubindex.rome.modules.prism;

import com.sun.syndication.feed.module.Module;

/**
 * RSS ROME module to parse the PRISM module for rss feeds
 * Publishing Requirements for Industry Standard Metadata (PRISM)
 * http://www.prismstandard.org/specifications/PRISM1[1].2.pdf
 */
public interface PrismModule extends Module{
  public static final String URI = "http://prismstandard.org/namespaces/1.2/basic/";
  public String getByteCount();
  public String getCategory();
  public String getComplianceProfile();
  public String getCopyright();
  public String getCorporateEntity();
  public String getCoverDate();
  public String getCoverDisplayDate();
  public String getCreationDate();
  public String getDistributor();
  public String getDoi();
  public String getEIssn();
  public String getEdition();
  public String getEmbargoDate();
  public String getEndingPage();
  public String getEvent();
  public String getExpirationDate();
  public String getHasAlternative();
  public String getHasCorrection();
  public String getHasFormat();
  public String getHasPart();
  public String getHasPreviousVersion();
  public String getHasTranslation();
  public String getIndustry();
  public String getIsCorrectionOf();
  public String getIsFormatOf();
  public String getIsPartOf();
  public String getIsReferencedBy();
  public String getIsRequiredBy();
  public String getIsTranslationOf();
  public String getIsVersionOf();
  public String getIssn();
  public String getIssueIdentifier();
  public String getIssueName();
  public String getLocation();
  public String getModificationDate();
  public String getNumber();
  public String getObjectTitle();
  public String getOrganization();
  public String getPerson();
  public String getPublicationDate();
  public String getPublicationName();
  public String getReceptionDate();
  public String getReferences();
  public String getRequires();
  public String getRightsAgent();
  public String getSection();
  public String getStartingPage();
  public String getSubsection();
  public String getTeaser();
  public String getVolume();
  public String getWordCount();

}
