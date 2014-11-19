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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.io.ModuleParser;

public class PrismModuleParser implements ModuleParser {

  // boilerplate
  public String getNamespaceUri() {
    return PrismModule.URI;
  }

  // implements the parsing for MyModule
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

  /**
   * This method gets called for the channel element and all item elements of the feed.
   * Create a prism module for each of them.
   */
  public Module parse(Element element) {
    Namespace prismNamespace = Namespace.getNamespace(PrismModule.URI);
    PrismModuleImpl prismModule = new PrismModuleImpl();

    List<Element> children = element.getChildren();
    for (Element e : children) {
      if (e.getNamespace().equals(prismNamespace)) {
        if (e.getName().equals("byteCount")) {
          prismModule.setByteCount(e.getTextTrim());
        } else if (e.getName().equals("category")) {
          prismModule.setCategory(e.getTextTrim());
        } else if (e.getName().equals("complianceProfile")) {
          prismModule.setComplianceProfile(e.getTextTrim());
        } else if (e.getName().equals("copyright")) {
          prismModule.setCopyright(e.getTextTrim());
        } else if (e.getName().equals("corporateEntity")) {
          prismModule.setCorporateEntity(e.getTextTrim());
        } else if (e.getName().equals("coverDate")) {
          prismModule.setCoverDate(e.getTextTrim());
        } else if (e.getName().equals("coverDisplayDate")) {
          prismModule.setCoverDisplayDate(e.getTextTrim());
        } else if (e.getName().equals("creationDate")) {
          prismModule.setCreationDate(e.getTextTrim());
        } else if (e.getName().equals("distributor")) {
          prismModule.setDistributor(e.getTextTrim());
        } else if (e.getName().equals("doi")) {
          prismModule.setDoi(e.getTextTrim());
        } else if (e.getName().equals("eIssn")) {
          prismModule.setEIssn(e.getTextTrim());
        } else if (e.getName().equals("edition")) {
          prismModule.setEdition(e.getTextTrim());
        } else if (e.getName().equals("embargoDate")) {
          prismModule.setEmbargoDate(e.getTextTrim());
        } else if (e.getName().equals("endingPage")) {
          prismModule.setEndingPage(e.getTextTrim());
        } else if (e.getName().equals("event")) {
          prismModule.setEvent(e.getTextTrim());
        } else if (e.getName().equals("expirationDate")) {
          prismModule.setExpirationDate(e.getTextTrim());
        } else if (e.getName().equals("hasAlternative")) {
          prismModule.setHasAlternative(e.getTextTrim());
        } else if (e.getName().equals("hasCorrection")) {
          prismModule.setHasCorrection(e.getTextTrim());
        } else if (e.getName().equals("hasFormat")) {
          prismModule.setHasFormat(e.getTextTrim());
        } else if (e.getName().equals("hasPart")) {
          prismModule.setHasPart(e.getTextTrim());
        } else if (e.getName().equals("hasPreviousVersion")) {
          prismModule.setHasPreviousVersion(e.getTextTrim());
        } else if (e.getName().equals("hasTranslation")) {
          prismModule.setHasTranslation(e.getTextTrim());
        } else if (e.getName().equals("industry")) {
          prismModule.setIndustry(e.getTextTrim());
        } else if (e.getName().equals("isCorrectionOf")) {
          prismModule.setIsCorrectionOf(e.getTextTrim());
        } else if (e.getName().equals("isFormatOf")) {
          prismModule.setIsFormatOf(e.getTextTrim());
        } else if (e.getName().equals("isPartOf")) {
          prismModule.setIsPartOf(e.getTextTrim());
        } else if (e.getName().equals("isReferencedBy")) {
          prismModule.setIsReferencedBy(e.getTextTrim());
        } else if (e.getName().equals("isRequiredBy")) {
          prismModule.setIsRequiredBy(e.getTextTrim());
        } else if (e.getName().equals("isTranslationOf")) {
          prismModule.setIsTranslationOf(e.getTextTrim());
        } else if (e.getName().equals("isVersionOf")) {
          prismModule.setIsVersionOf(e.getTextTrim());
        } else if (e.getName().equals("issn")) {
          prismModule.setIssn(e.getTextTrim());
        } else if (e.getName().equals("issueIdentifier")) {
          prismModule.setIssueIdentifier(e.getTextTrim());
        } else if (e.getName().equals("issueName")) {
          prismModule.setIssueName(e.getTextTrim());
        } else if (e.getName().equals("location")) {
          prismModule.setLocation(e.getTextTrim());
        } else if (e.getName().equals("modificationDate")) {
          prismModule.setModificationDate(e.getTextTrim());
        } else if (e.getName().equals("number")) {
          prismModule.setNumber(e.getTextTrim());
        } else if (e.getName().equals("objectTitle")) {
          prismModule.setObjectTitle(e.getTextTrim());
        } else if (e.getName().equals("organization")) {
          prismModule.setOrganization(e.getTextTrim());
        } else if (e.getName().equals("person")) {
          prismModule.setPerson(e.getTextTrim());
        } else if (e.getName().equals("publicationDate")) {
          prismModule.setPublicationDate(e.getTextTrim());
        } else if (e.getName().equals("publicationName")) {
          prismModule.setPublicationName(e.getTextTrim());
        } else if (e.getName().equals("receptionDate")) {
          prismModule.setReceptionDate(e.getTextTrim());
        } else if (e.getName().equals("references")) {
          prismModule.setReferences(e.getTextTrim());
        } else if (e.getName().equals("requires")) {
          prismModule.setRequires(e.getTextTrim());
        } else if (e.getName().equals("rightsAgent")) {
          prismModule.setRightsAgent(e.getTextTrim());
        } else if (e.getName().equals("section")) {
          prismModule.setSection(e.getTextTrim());
        } else if (e.getName().equals("startingPage")) {
          prismModule.setStartingPage(e.getTextTrim());
        } else if (e.getName().equals("subsection")) {
          prismModule.setSubsection(e.getTextTrim());
        } else if (e.getName().equals("teaser")) {
          prismModule.setTeaser(e.getTextTrim());
        } else if (e.getName().equals("volume")) {
          prismModule.setVolume(e.getTextTrim());
        } else if (e.getName().equals("wordCount")) {
          prismModule.setWordCount(e.getTextTrim());
        }
      }
    }
    return prismModule;
  }
}
