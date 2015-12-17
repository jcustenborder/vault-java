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
import com.github.jcustenborder.vault.client.model.Secret;
import com.google.api.client.json.Json;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class HttpLogicalClientTest extends BaseHttpClientTest {
  @Test
  public void read() throws IOException {
    String path="cubbyhole/foo";

    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"lease_id\":\"\",\"renewable\":false,\"lease_duration\":0,\"data\":{\"boolean\":true,\"integer\":0,\"string\":\"asdfasd\"},\"warnings\":null,\"auth\":null}",
        "https://127.0.0.1:8200/v1/cubbyhole/foo"
    );

    final ImmutableMap<String, Object> expectedData = ImmutableMap.of("boolean",true, "integer", (Object)BigDecimal.ZERO, "string", "asdfasd");
    final Secret actual = vaultClient.getLogical().read(path);
    Assert.assertNotNull(actual);
    Assert.assertNotNull(actual.getData());
    Assert.assertTrue(Maps.difference(expectedData, actual.getData()).areEqual());
  }

  @Test
  public void readNotFound() throws IOException {
    final String path = "cubbyhole/foo";
    final VaultClient vaultClient = getVaultClient(404, "application/json", "", "https://127.0.0.1:8200/v1/cubbyhole/foo");
    final Secret actual = vaultClient.getLogical().read(path);
    Assert.assertNull(actual);
  }

  @Test
  public void writeNoLease() throws IOException {
    final String path = "cubbyhole/foo";
    final VaultClient vaultClient = getVaultClient(204, "application/json", "", "https://127.0.0.1:8200/v1/cubbyhole/foo");
    ImmutableMap<String, Object> data = ImmutableMap.of("integer", (Object)0, "string", "asdfasd", "boolean", true);
    final Secret actual = vaultClient.getLogical().write(path, data);
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getLeaseID());
  }

  @Test
  public void delete() throws IOException {
    final String path = "cubbyhole/foo";
    final VaultClient vaultClient = getVaultClient(204, "application/json", "", "https://127.0.0.1:8200/v1/cubbyhole/foo");
    final boolean actual = vaultClient.getLogical().delete(path);
    Assert.assertTrue(actual);
  }

  @Test
  public void deleteNotFound() throws IOException {
    final String path = "cubbyhole/foo";
    final VaultClient vaultClient = getVaultClient(404, "application/json", "", "https://127.0.0.1:8200/v1/cubbyhole/foo");
    final boolean actual = vaultClient.getLogical().delete(path);
    Assert.assertFalse(actual);
  }
}
