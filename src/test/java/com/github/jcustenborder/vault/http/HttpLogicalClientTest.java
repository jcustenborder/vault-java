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
package com.github.jcustenborder.vault.http;


import com.github.jcustenborder.vault.client.LogicalClient;
import com.github.jcustenborder.vault.client.VaultClient;
import com.github.jcustenborder.vault.client.http.HttpVaultClientBuilder;
import com.github.jcustenborder.vault.client.model.Secret;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpLogicalClientTest {

  LogicalClient logicalClient;

  @Before
  public void before(){
    VaultClient client =  new HttpVaultClientBuilder()
        .withToken("361ee26c-6684-d6d2-cbf5-f264f22b0f7b")
        .withUrl("http://127.0.0.1:8200/v1/")
        .withEnableCurlRequestLogging(true)
        .withEnableRequestLogging(true)
        .build();
    this.logicalClient = client.getLogical();
  }

  @Test
  public void cubbyHole() throws IOException {
    final String path = String.format("cubbyhole/%s.cubbyHole", this.getClass().getName());
    ImmutableMap<String, Object> data = ImmutableMap.of("integer", (Object)0, "string", "asdfasd", "boolean", true);
    Secret writtenSecret = this.logicalClient.write(path, data);
    Assert.assertNotNull("write should return a secret.", writtenSecret);
    Secret readSecret = this.logicalClient.read(path);
    Assert.assertNotNull("secret should have been found.", readSecret);
    Assert.assertTrue("delete call should have been successful.", this.logicalClient.delete(path));
  }

  @Test
  public void readNotFound() throws IOException {
    final String path = String.format("cubbyhole/%s.readNotFound", this.getClass().getName());
    Secret readSecret = this.logicalClient.read(path);
    Assert.assertNull("secret should not have been found.", readSecret);
  }
}
