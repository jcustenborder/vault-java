package com.github.jcustenborder.vault.client.http;

import com.github.jcustenborder.vault.client.LogicalClient;
import com.github.jcustenborder.vault.client.VaultException;
import com.github.jcustenborder.vault.client.model.ErrorResponse;
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
    return response.isSuccessStatusCode();
  }



  @Override
  public Secret read(String path) throws IOException {
    List<String> pathParts = getPath(path);
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpRequest httpRequest = super.httpRequestFactory.buildGetRequest(requestUrl);
    HttpResponse response = httpRequest.execute();

    if(response.isSuccessStatusCode()){
      return response.parseAs(Secret.class);
    } else {
      if(404 == response.getStatusCode()){
        //This happens where there is no secret.
        return null;
      } else {
        ErrorResponse errorResponse = response.parseAs(ErrorResponse.class);
        throw new VaultException(errorResponse.getErrors());
      }
    }
  }

  @Override
  public Secret write(String path, Map<String, Object> data) throws IOException {
    List<String> pathParts = getPath(path);
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpContent httpContent = getJsonHttpContent(data);
    HttpRequest httpRequest = super.httpRequestFactory.buildPutRequest(requestUrl, httpContent);
    HttpResponse response = httpRequest.execute();
    if(response.isSuccessStatusCode()){
      if(204 == response.getStatusCode()){
        //This happens where there is no lease defined.
        return new Secret();
      } else {
        return response.parseAs(Secret.class);
      }
    } else {
      ErrorResponse errorResponse = response.parseAs(ErrorResponse.class);
      throw new VaultException(errorResponse.getErrors());
    }
  }
}
