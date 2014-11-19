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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ArticleTest {

  @Test
  public void testSetExtractedText() throws Exception {
    Article a = new Article();
    a.setExtractedText(" Hallo  my dear");
    assertEquals(" Hallo  my dear", a.getExtractedText());

    StringBuilder text = new StringBuilder();
    text.append('\u0000');
    text.append("Carla");
    a.setExtractedText(text.toString());
    assertEquals("Carla", a.getExtractedText());

    text.append('\u003d');
    text.append('\u0000');
    a.setExtractedText(text.toString());
    assertEquals("Carla=", a.getExtractedText());
  }
}
