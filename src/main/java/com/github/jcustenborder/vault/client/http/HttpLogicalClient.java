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
