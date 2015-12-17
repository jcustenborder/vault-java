/**
 * Copyright 2015 Jeremy Custenborder
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
package com.github.jcustenborder.vault.client.http;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BaseHttpClient {
  protected final GenericUrl baseUrl;
  protected final ImmutableList<String> basePathParts;
  protected final HttpRequestFactory httpRequestFactory;
  private final JsonFactory jsonFactory = new GsonFactory();

  public BaseHttpClient(HttpRequestFactory httpRequestFactory, GenericUrl baseUrl, ImmutableList<String> basePathParts) {
    this.httpRequestFactory = httpRequestFactory;
    this.baseUrl = baseUrl;
    this.basePathParts = basePathParts;
  }

  protected JsonHttpContent getJsonHttpContent(Object data){
    return new JsonHttpContent(jsonFactory, data);
  }

  protected List<String> getPath(String path) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path), "path cannot be null.");
    String[] secretParts = path.split("/");
    Preconditions.checkArgument(secretParts.length>=2, "path must be in <mount>/<secret> format.");
    List<String> pathParts = new ArrayList<>(secretParts.length + basePathParts.size());
    pathParts.add("");
    pathParts.addAll(this.basePathParts);
    pathParts.addAll(Arrays.asList(secretParts));
    return pathParts;
  }
}
