package com.github.jcustenborder.vault.client;

import com.github.jcustenborder.vault.client.model.Secret;
import com.google.api.client.http.*;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class HttpLogicalClient extends BaseHttpClient implements LogicalClient {


  public HttpLogicalClient(HttpRequestFactory httpRequestFactory, GenericUrl baseUrl, ImmutableList<String> basePathParts) {
    super(httpRequestFactory, baseUrl, basePathParts);
  }

  @Override
  public Boolean delete(String path) throws IOException {
    List<String> pathParts = getPath(path);
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpRequest httpRequest = super.httpRequestFactory.buildDeleteRequest(requestUrl);
    HttpResponse response = httpRequest.execute();
    return response.parseAs(Boolean.class);
  }

  @Override
  public Secret read(String path) throws IOException {
    List<String> pathParts = getPath(path);
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpRequest httpRequest = super.httpRequestFactory.buildGetRequest(requestUrl);
    HttpResponse response = httpRequest.execute();
    return response.parseAs(Secret.class);
  }

  @Override
  public Secret write(String path, Map<String, Object> data) throws IOException {
    List<String> pathParts = getPath(path);
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpContent httpContent = getJsonHttpContent(data);
    System.out.println(requestUrl);
    HttpRequest httpRequest = super.httpRequestFactory.buildPutRequest(requestUrl, httpContent);
    HttpResponse response = httpRequest.execute();
    return response.parseAs(Secret.class);
  }
}
