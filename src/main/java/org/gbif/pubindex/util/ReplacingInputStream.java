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

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.util.ByteArrayBuilder;


public class ReplacingInputStream extends InputStream{
  private final byte[] replaceArray;
  private final byte[] withArray;
  private InputStream stream;
  private byte[] buffer = new byte[0];
  private int pointer = 0;


  /**
   * All strings have to be ASCII string, no extended encoding supported!
   *
   * @param in
   * @param replace search string to be replaced
   * @param with string to replace search string with
   */
  public ReplacingInputStream(InputStream in, String replace, String with) {
    this.replaceArray = replace.getBytes();
    this.withArray = with.getBytes();
    this.stream=in;
  }

  @Override
  public int read() throws IOException {
    // we have buffered some safe, unreplaced or already replaced bytes
    if (pointer < buffer.length) {
      pointer++;
      return buffer[pointer - 1];
    }
    // check if new bytes match replace sequence

    // check whole buffer
    int nb = stream.read();
    if (nb==replaceArray[0]){
      ByteArrayBuilder builder = new ByteArrayBuilder();
      int idx = 1;
      while (true){
        if (idx==replaceArray.length){
          // matched the whole string
          // use replacement "buffer"
          buffer = withArray.clone();
          // use first byte already
          nb = buffer[0];
          pointer = 1;
          break;
        }

        int x = stream.read();
        builder.append(x);
        if (x != replaceArray[idx]) {
          // not matching anymore - keep checked bytes in buffer to be reserved
          buffer = builder.toByteArray();
          pointer = 0;
          break;
        }
        idx++;
      }
    }
    return nb;
  }

}
