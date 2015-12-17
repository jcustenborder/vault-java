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

import com.github.jcustenborder.vault.client.SysClient;
import com.github.jcustenborder.vault.client.VaultException;
import com.github.jcustenborder.vault.client.model.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.ArrayMap;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class HttpSysClient extends BaseHttpClient implements SysClient {
  public HttpSysClient(HttpRequestFactory httpRequestFactory, GenericUrl baseUrl, ImmutableList<String> basePathParts) {
    super(httpRequestFactory, baseUrl, basePathParts);
  }

  @Override
  public Map<String, Object> audits() {
    return null;
  }


  <T extends SetByMap> Map<String, T> setByMap(Map<String, Map<String, String>> input, Class<T> type) throws IOException {
    try {
      Map<String, T> output = new ArrayMap<>();
      for (Map.Entry<String, Map<String, String>> entry : input.entrySet()) {
        T value = type.newInstance();
        value.setByMap(entry.getValue());
        output.put(entry.getKey(),value);
      }
      return output;
    } catch(Exception ex){
      throw new IOException("Exception thrown while building map", ex);
    }
  }


  @Override
  public Map<String, Auth> auths() throws IOException {
    List<String> pathParts = getPath("auth");
    GenericUrl requestUrl = this.baseUrl.clone();
    requestUrl.setPathParts(pathParts);
    HttpRequest httpRequest = super.httpRequestFactory.buildGetRequest(requestUrl);
    HttpResponse response = httpRequest.execute();
    if(response.isSuccessStatusCode()){
      Map<String, Map<String, String>> tmpresponse = response.parseAs(Map.class);
      return setByMap(tmpresponse, Auth.class);
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
  public Object deletePolicy(String name) {
    return null;
  }

  @Override
  public Boolean disableAudit(String path) {
    return false;
  }

  @Override
  public Boolean disableAuth(String path) {
    return false;
  }

  @Override
  public Boolean enableAudit(String path, String type, String description, Map<String, Object> options) {
    return false;
  }

  @Override
  public Boolean enableAuth(String path, String type, String description) {
    return false;
  }

  @Override
  public Object init(Map<String, Object> options) {
    return null;
  }

  @Override
  public Object initStatus() {
    return null;
  }

  @Override
  public LeaderStatus leader() {
    return null;
  }

  @Override
  public Object mount(String path, String type, String description) {
    return null;
  }

  @Override
  public Map<String, Mount> mounts() {
    return null;
  }

  @Override
  public PolicyResponse policies() {
    return null;
  }

  @Override
  public Object readPolicy(String name) {
    return null;
  }

  @Override
  public Object writePolicy(String name, String rules) {
    return null;
  }

  @Override
  public Boolean remount(String from, String to) {
    return false;
  }

  @Override
  public Object renew(String id, int increment) {
    return null;
  }

  @Override
  public Boolean revoke(String id) {
    return false;
  }

  @Override
  public Boolean revoke_prefix(String id) {
    return false;
  }

  @Override
  public Boolean seal() {
    return false;
  }

  @Override
  public Object sealStatus() {
    return null;
  }

  @Override
  public Boolean unmount(String path) {
    return false;
  }

  @Override
  public Object unseal(String shard) {
    return null;
  }
}
