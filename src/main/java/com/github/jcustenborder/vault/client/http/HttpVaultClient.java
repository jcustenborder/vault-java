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
import com.github.jcustenborder.vault.client.SysClient;
import com.github.jcustenborder.vault.client.VaultClient;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HttpVaultClient implements VaultClient {
  private final HttpVaultClientSettings httpVaultClientSettings;
  private final HttpRequestFactory httpRequestFactory;
  private final HttpHeaders httpHeaders;
  private final HttpTransport httpTransport;
  private final JsonFactory jsonFactory = new GsonFactory();
  private final String USER_AGENT = "VaultJava/0.0.0 (+github.com/jcustenborder/vault-java)";
  private final LogicalClient logicalClient;
  private final SysClient sysClient;

  public HttpVaultClient(HttpTransport httpTransport, final HttpVaultClientSettings httpVaultClientSettings){
    Preconditions.checkNotNull(httpTransport, "httpTransport cannot be null.");
    Preconditions.checkNotNull(httpVaultClientSettings, "httpVaultClientSettings cannot be null.");
    this.httpVaultClientSettings = httpVaultClientSettings;
    this.httpVaultClientSettings.validate();
    this.httpTransport = httpTransport;
    this.httpHeaders = new HttpHeaders();
    this.httpHeaders.set("X-Vault-Token", this.httpVaultClientSettings.getToken());
    this.httpHeaders.setUserAgent(USER_AGENT);
    this.httpRequestFactory = this.httpTransport.createRequestFactory(
        new HttpRequestInitializer() {
          @Override
          public void initialize(HttpRequest request) throws IOException {
            request.setParser(new JsonObjectParser(jsonFactory));
            request.setHeaders(httpHeaders);
            request.setLoggingEnabled(httpVaultClientSettings.isRequestLoggingEnabled());
            request.setCurlLoggingEnabled(httpVaultClientSettings.isRequestCurlLoggingEnabled());
            if(null!=httpVaultClientSettings.getNumberOfRetries()){
              request.setNumberOfRetries(httpVaultClientSettings.getNumberOfRetries());
            }
            request.setSuppressUserAgentSuffix(true);
            request.setThrowExceptionOnExecuteError(false);
          }
        }
    );
    GenericUrl baseUrl = new GenericUrl(this.httpVaultClientSettings.getUrl());
    List<String> pathParts = baseUrl.getPathParts();
    List<String> clean = new ArrayList<>();
    for(String s:pathParts){
      if(!Strings.isNullOrEmpty(s))
        clean.add(s);
    }

    ImmutableList<String> basePathParts = ImmutableList.copyOf(clean);
    this.logicalClient = new HttpLogicalClient(this.httpRequestFactory, baseUrl, basePathParts);
    this.sysClient = new HttpSysClient(this.httpRequestFactory, baseUrl, basePathParts);
  }

  public HttpVaultClient(HttpVaultClientSettings httpVaultClientSettings){
    this(new NetHttpTransport(), httpVaultClientSettings);
  }

  @Override
  public LogicalClient getLogical() {
    return this.logicalClient;
  }

  @Override
  public SysClient getSys() {
    return this.sysClient;
  }
}
