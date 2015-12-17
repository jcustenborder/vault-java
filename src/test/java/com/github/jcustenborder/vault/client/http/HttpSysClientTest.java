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
import com.github.jcustenborder.vault.client.model.Mount;
import com.google.api.client.json.Json;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class HttpSysClientTest extends BaseHttpClientTest {
  @Test
  public void auths() throws IOException {
    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"token/\":{\"description\":\"token based credentials\",\"type\":\"token\"}}",
        "https://127.0.0.1:8200/v1/sys/auth"
    );

    Map<String, Auth> result = vaultClient.getSys().auths();
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    for(Map.Entry<String, Auth> entry:result.entrySet()){
      Assert.assertEquals(String.class, entry.getKey().getClass());
      Assert.assertEquals(Auth.class, entry.getValue().getClass());
    }
  }

  @Test
  public void mounts() throws IOException {
    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"cubbyhole/\":{\"config\":{\"default_lease_ttl\":0,\"max_lease_ttl\":0},\"description\":\"per-token private secret storage\",\"type\":\"cubbyhole\"},\"secret/\":{\"config\":{\"default_lease_ttl\":0,\"max_lease_ttl\":0},\"description\":\"generic secret storage\",\"type\":\"generic\"},\"sys/\":{\"config\":{\"default_lease_ttl\":0,\"max_lease_ttl\":0},\"description\":\"system endpoints used for control, policy and debugging\",\"type\":\"system\"}}",
        "https://127.0.0.1:8200/v1/sys/auth"
    );

    Map<String, Mount> result = vaultClient.getSys().mounts();
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    for(Map.Entry<String, Mount> entry:result.entrySet()){
      Assert.assertEquals(String.class, entry.getKey().getClass());
      Assert.assertEquals(Mount.class, entry.getValue().getClass());
    }

    Mount mount = result.get("cubbyhole/");
    Assert.assertNotNull("mount for cubbyhole/ was not found.", mount);
    Assert.assertNotNull("mount config for cubbyhole/ should not be null.", mount.getConfig());
    Assert.assertEquals("mount cubbyhole/ config.default_lease_ttl does not match", (Integer)0, mount.getConfig().getDefaultLeaseTTL());
    Assert.assertEquals("mount cubbyhole/ config.max_lease_ttl does not match", (Integer)0, mount.getConfig().getMaxLeaseTTL());
    Assert.assertEquals("mount cubbyhole/ description does not match", "per-token private secret storage", mount.getDescription());
    Assert.assertEquals("mount cubbyhole/ type does not match", "cubbyhole", mount.getType());
  }


}
