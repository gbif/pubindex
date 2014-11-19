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
package org.gbif.pubindex.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DoiUtilsTest {

  @Test
  public void testExtractDoi(){
    assertEquals("10.1111/j.1654-1103.2011.01343.x", DoiUtils.extractDoi("http://dx.doi.org/10.1111/j.1654-1103.2011.01343.x"));
    assertEquals("10.1111/j.1654-1103.2011.01343.x", DoiUtils.extractDoi("doi:10.1111/j.1654-1103.2011.01343.x"));
    assertEquals("10.1111/j.1654-1103.2011.01343.x", DoiUtils.extractDoi("10.1111/j.1654-1103.2011.01343.x"));

    assertEquals("10.1371/journal.pbio.0020449", DoiUtils.extractDoi("  doi:10.1371/journal.pbio.0020449"));
    assertEquals("10.1000/182", DoiUtils.extractDoi("http://dx.doi.org/10.1000/182"));
  }
}
