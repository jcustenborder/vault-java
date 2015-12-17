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
