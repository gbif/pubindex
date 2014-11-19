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
package org.gbif.pubindex.manager.impl;

import java.security.PublicKey;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ArticleIndexerImplTest {

  @Test
  public void testCleanTfArtifacts(){
    ArticleIndexerImpl ai = new ArticleIndexerImpl(null,null,null,null);
    assertEquals("Abies alba", ai.cleanTfArtifacts("Abies alba"));
    assertEquals("Abies alba Mill.", ai.cleanTfArtifacts("Abies alba Mill."));
    assertEquals("Abies alba", ai.cleanTfArtifacts("A[bies] alba"));
  }

}
