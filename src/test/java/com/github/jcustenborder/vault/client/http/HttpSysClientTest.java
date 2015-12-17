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
import com.github.jcustenborder.vault.client.model.Auth;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class HttpSysClientTest extends BaseHttpClientTest {
  @Test
  public void auths() throws IOException {
    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
            response.setStatusCode(200);
            response.setContentType(Json.MEDIA_TYPE);
            response.setContent("{\"token/\":{\"description\":\"token based credentials\",\"type\":\"token\"}}");
            return response;
          }
        };
      }
    };

    VaultClient vaultClient = getVaultClient(transport);

    Map<String, Auth> result = vaultClient.getSys().auths();
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    for(Map.Entry<String, Auth> entry:result.entrySet()){
      Assert.assertEquals(String.class, entry.getKey().getClass());
      Assert.assertEquals(Auth.class, entry.getValue().getClass());
    }
  }
}
