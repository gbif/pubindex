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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jdom.Element;
import org.jdom.Namespace;

import com.sun.syndication.feed.module.Module;
import com.sun.syndication.io.ModuleGenerator;

public class PrismModuleGenerator implements ModuleGenerator {
  private static final Namespace NAMESPACE = Namespace.getNamespace("prism", PrismModule.URI);
  private static final Set NAMESPACES;
  static {
    Set<Namespace> namespaces = new HashSet<Namespace>();
    namespaces.add(NAMESPACE);
    NAMESPACES = Collections.unmodifiableSet(namespaces);
  }

  @Override
  public String getNamespaceUri() {
    return PrismModule.URI;
  }

  @Override
  public Set getNamespaces() {
    return NAMESPACES;
  }

  @Override
  public void generate(Module module, Element element) {
    // TODO: Write implementation
  }
}
