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

import org.gbif.utils.file.InputStreamUtils;

import java.io.InputStream;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PrismModuleParserTest {

  @Test
  public void testParse() throws Exception {
    // read feed object
    SyndFeed feed=null;
    SyndFeedInput input = new SyndFeedInput();
    InputStream stream= new InputStreamUtils().classpathStream("prism-rss2.xml");
    feed = input.build(new XmlReader(stream));

    assertEquals("Journal of Vegetation Science", feed.getTitle());

    PrismModule mod = (PrismModule) feed.getModule(PrismModule.URI);
    assertNotNull(mod);
    assertEquals("1100-9233", mod.getIssn());
    assertEquals("1654-1103", mod.getEIssn());

  }
}
