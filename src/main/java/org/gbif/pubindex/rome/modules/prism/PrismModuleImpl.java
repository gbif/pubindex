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

import java.lang.reflect.InvocationTargetException;

import com.sun.syndication.feed.module.ModuleImpl;
import org.apache.commons.beanutils.BeanUtils;

public class PrismModuleImpl extends ModuleImpl implements PrismModule{
  private static final long serialVersionUID = -8275118704842545845L;

  private String byteCount;
  private String category;
  private String complianceProfile;
  private String copyright;
  private String corporateEntity;
  private String coverDate;
  private String coverDisplayDate;
  private String creationDate;
  private String distributor;
  private String doi;
  private String eIssn;
  private String edition;
  private String embargoDate;
  private String endingPage;
  private String event;
  private String expirationDate;
  private String hasAlternative;
  private String hasCorrection;
  private String hasFormat;
  private String hasPart;
  private String hasPreviousVersion;
  private String hasTranslation;
  private String industry;
  private String isCorrectionOf;
  private String isFormatOf;
  private String isPartOf;
  private String isReferencedBy;
  private String isRequiredBy;
  private String isTranslationOf;
  private String isVersionOf;
  private String issn;
  private String issueIdentifier;
  private String issueName;
  private String location;
  private String modificationDate;
  private String number;
  private String objectTitle;
  private String organization;
  private String person;
  private String publicationDate;
  private String publicationName;
  private String receptionDate;
  private String references;
  private String requires;
  private String rightsAgent;
  private String section;
  private String startingPage;
  private String subsection;
  private String teaser;
  private String volume;
  private String wordCount;

  public PrismModuleImpl() {
    super(PrismModule.class, PrismModule.URI);
  }

  @Override
  public Class getInterface() {
    return PrismModule.class;
  }

  @Override
  public void copyFrom(Object obj) {
    PrismModule mod = (PrismModule) obj;
    try {
      BeanUtils.copyProperties(this, mod);
    } catch (IllegalAccessException e) {
      // swallow
    } catch (InvocationTargetException e) {
      // swallow
    }
  }

  public String getByteCount() {
    return byteCount;
  }

  public void setByteCount(String byteCount) {
    this.byteCount = byteCount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getComplianceProfile() {
    return complianceProfile;
  }

  public void setComplianceProfile(String complianceProfile) {
    this.complianceProfile = complianceProfile;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getCorporateEntity() {
    return corporateEntity;
  }

  public void setCorporateEntity(String corporateEntity) {
    this.corporateEntity = corporateEntity;
  }

  public String getCoverDate() {
    return coverDate;
  }

  public void setCoverDate(String coverDate) {
    this.coverDate = coverDate;
  }

  public String getCoverDisplayDate() {
    return coverDisplayDate;
  }

  public void setCoverDisplayDate(String coverDisplayDate) {
    this.coverDisplayDate = coverDisplayDate;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public String getDoi() {
    return doi;
  }

  public void setDoi(String doi) {
    this.doi = doi;
  }

  public String getDistributor() {
    return distributor;
  }

  public void setDistributor(String distributor) {
    this.distributor = distributor;
  }

  public String getEIssn() {
    return eIssn;
  }

  public void setEIssn(String eIssn) {
    this.eIssn = eIssn;
  }

  public String getEdition() {
    return edition;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public String getEmbargoDate() {
    return embargoDate;
  }

  public void setEmbargoDate(String embargoDate) {
    this.embargoDate = embargoDate;
  }

  public String getEndingPage() {
    return endingPage;
  }

  public void setEndingPage(String endingPage) {
    this.endingPage = endingPage;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getHasAlternative() {
    return hasAlternative;
  }

  public void setHasAlternative(String hasAlternative) {
    this.hasAlternative = hasAlternative;
  }

  public String getHasCorrection() {
    return hasCorrection;
  }

  public void setHasCorrection(String hasCorrection) {
    this.hasCorrection = hasCorrection;
  }

  public String getHasFormat() {
    return hasFormat;
  }

  public void setHasFormat(String hasFormat) {
    this.hasFormat = hasFormat;
  }

  public String getHasPart() {
    return hasPart;
  }

  public void setHasPart(String hasPart) {
    this.hasPart = hasPart;
  }

  public String getHasPreviousVersion() {
    return hasPreviousVersion;
  }

  public void setHasPreviousVersion(String hasPreviousVersion) {
    this.hasPreviousVersion = hasPreviousVersion;
  }

  public String getHasTranslation() {
    return hasTranslation;
  }

  public void setHasTranslation(String hasTranslation) {
    this.hasTranslation = hasTranslation;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getIsCorrectionOf() {
    return isCorrectionOf;
  }

  public void setIsCorrectionOf(String correctionOf) {
    isCorrectionOf = correctionOf;
  }

  public String getIsFormatOf() {
    return isFormatOf;
  }

  public void setIsFormatOf(String formatOf) {
    isFormatOf = formatOf;
  }

  public String getIsPartOf() {
    return isPartOf;
  }

  public void setIsPartOf(String partOf) {
    isPartOf = partOf;
  }

  public String getIsReferencedBy() {
    return isReferencedBy;
  }

  public void setIsReferencedBy(String referencedBy) {
    isReferencedBy = referencedBy;
  }

  public String getIsRequiredBy() {
    return isRequiredBy;
  }

  public void setIsRequiredBy(String requiredBy) {
    isRequiredBy = requiredBy;
  }

  public String getIsTranslationOf() {
    return isTranslationOf;
  }

  public void setIsTranslationOf(String translationOf) {
    isTranslationOf = translationOf;
  }

  public String getIsVersionOf() {
    return isVersionOf;
  }

  public void setIsVersionOf(String versionOf) {
    isVersionOf = versionOf;
  }

  public String getIssn() {
    return issn;
  }

  public void setIssn(String issn) {
    this.issn = issn;
  }

  public String getIssueIdentifier() {
    return issueIdentifier;
  }

  public void setIssueIdentifier(String issueIdentifier) {
    this.issueIdentifier = issueIdentifier;
  }

  public String getIssueName() {
    return issueName;
  }

  public void setIssueName(String issueName) {
    this.issueName = issueName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(String modificationDate) {
    this.modificationDate = modificationDate;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getObjectTitle() {
    return objectTitle;
  }

  public void setObjectTitle(String objectTitle) {
    this.objectTitle = objectTitle;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getPerson() {
    return person;
  }

  public void setPerson(String person) {
    this.person = person;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getPublicationName() {
    return publicationName;
  }

  public void setPublicationName(String publicationName) {
    this.publicationName = publicationName;
  }

  public String getReceptionDate() {
    return receptionDate;
  }

  public void setReceptionDate(String receptionDate) {
    this.receptionDate = receptionDate;
  }

  public String getReferences() {
    return references;
  }

  public void setReferences(String references) {
    this.references = references;
  }

  public String getRequires() {
    return requires;
  }

  public void setRequires(String requires) {
    this.requires = requires;
  }

  public String getRightsAgent() {
    return rightsAgent;
  }

  public void setRightsAgent(String rightsAgent) {
    this.rightsAgent = rightsAgent;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public String getStartingPage() {
    return startingPage;
  }

  public void setStartingPage(String startingPage) {
    this.startingPage = startingPage;
  }

  public String getSubsection() {
    return subsection;
  }

  public void setSubsection(String subsection) {
    this.subsection = subsection;
  }

  public String getTeaser() {
    return teaser;
  }

  public void setTeaser(String teaser) {
    this.teaser = teaser;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getWordCount() {
    return wordCount;
  }

  public void setWordCount(String wordCount) {
    this.wordCount = wordCount;
  }
}
