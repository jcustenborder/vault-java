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


import com.github.jcustenborder.vault.client.VaultClient;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Assert;

import java.io.IOException;

public class BaseHttpClientTest {
  VaultClient getVaultClient(HttpTransport transport) {
    HttpVaultClientSettings settings = new HttpVaultClientSettings();
    settings.setToken("asdfasdf");
    settings.setUrl("https://127.0.0.1:8200/v1/");
    settings.httpTransport = transport;
    return new HttpVaultClientBuilder(settings).build();
  }
  protected VaultClient getVaultClient(final MockLowLevelHttpResponse response, final String expectedUrl){
    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, final String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            Assert.assertEquals("The requested url is does not match.", expectedUrl, url);
            return response;
          }
        };
      }
    };
    return getVaultClient(transport);
  }

  protected VaultClient getVaultClient(int statusCode, String contentType, String content, final String expectedUrl){
    MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
    response.setStatusCode(statusCode);
    response.setContentType(contentType);
    response.setContent(content);
    return getVaultClient(response, expectedUrl);
  }
}
