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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ReplacingInputStreamTest {

  @Test
  public void testReplacing() throws IOException {
    InputStream in = new ByteArrayInputStream("Bitte weg <damit> und nicht <domit> ooder damit.".getBytes());
    ReplacingInputStream stream = new ReplacingInputStream(in,"<damit>", "schlaumeier");

    StringWriter writer = new StringWriter();
    IOUtils.copy(stream, writer, "UTF8");
    String theString = writer.toString();

    System.out.print(theString);
    assertEquals("Bitte weg schlaumeier und nicht <domit> ooder damit.", theString);
  }
}
