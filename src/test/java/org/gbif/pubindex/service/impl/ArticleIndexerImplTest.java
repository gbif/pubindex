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
package org.gbif.pubindex.service.impl;

import org.gbif.pubindex.config.PubindexConfig;
import org.gbif.pubindex.model.Article;

import java.io.StringReader;

import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ArticleIndexerImplTest {

  @Test
  public void testCleanTfArtifacts(){
    ArticleIndexerImpl ai = new ArticleIndexerImpl(new PubindexConfig(), null,null,null,null);
    assertEquals("Abies alba", ai.cleanTfArtifacts("Abies alba"));
    assertEquals("Abies alba Mill.", ai.cleanTfArtifacts("Abies alba Mill."));
    assertEquals("Abies alba", ai.cleanTfArtifacts("A[bies] alba"));
  }

  @Test
  @Ignore
  public void testTextExtraction(){
    ArticleIndexerImpl ai = new ArticleIndexerImpl(new PubindexConfig(), null,null,null,null);
    Article a = new Article();
    a.setId(1);

    // needs to be html/xml/rdf
    assertNull(ai.extractText(a, new ReaderInputStream(new StringReader(" Hallo  my dear"))));

    assertEquals(" Hallo  my dear", ai.extractText(a, new ReaderInputStream(new StringReader("<html><body> Hallo  my dear</body></html>"))));

    StringBuilder text = new StringBuilder();
    text.append("<p>");
    text.append('\u0000');
    text.append("Carla");
    text.append("</p>");
    assertEquals("Carla", ai.extractText(a, new ReaderInputStream(new StringReader(text.toString()))));

    text.append("<p>");
    text.append('\u003d');
    text.append('\u0000');
    text.append("</p>");
    assertEquals("Carla=", ai.extractText(a, new ReaderInputStream(new StringReader(text.toString()))));
  }

}
