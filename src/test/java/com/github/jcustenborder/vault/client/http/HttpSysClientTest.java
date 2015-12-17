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
import com.github.jcustenborder.vault.client.VaultException;
import com.github.jcustenborder.vault.client.model.*;
import com.google.api.client.json.Json;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

  @Test(expected = VaultException.class)
  public void authsBadRequest() throws IOException {
    VaultClient vaultClient = getVaultClient(400,
        Json.MEDIA_TYPE,
        "{\"errors\":[\"missing client token\"]}",
        "https://127.0.0.1:8200/v1/sys/auth"
    );

    vaultClient.getSys().auths();
  }


  @Test(expected = VaultException.class)
  public void mountsBadRequest() throws IOException {
    VaultClient vaultClient = getVaultClient(400,
        Json.MEDIA_TYPE,
        "{\"errors\":[\"missing client token\"]}",
        "https://127.0.0.1:8200/v1/sys/auth"
    );

    vaultClient.getSys().mounts();
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

  @Test
  public void initStatus() throws IOException{
    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"initialized\":true}",
        "https://127.0.0.1:8200/v1/sys/init"
    );

    InitStatus initStatus = vaultClient.getSys().initStatus();
    Assert.assertNotNull(initStatus);
    Assert.assertEquals(true, initStatus.getInitialized());
  }

  @Test(expected = VaultException.class)
  public void initStatusBadRequest() throws IOException {
    VaultClient vaultClient = getVaultClient(400,
        Json.MEDIA_TYPE,
        "{\"errors\":[\"missing client token\"]}",
        "https://127.0.0.1:8200/v1/sys/init"
    );

    vaultClient.getSys().initStatus();
  }

  @Test
  public void leaderStatus() throws IOException{
    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"ha_enabled\":false,\"is_self\":false,\"leader_address\":\"\"}",
        "https://127.0.0.1:8200/v1/sys/leader"
    );

    LeaderStatus leaderStatus = vaultClient.getSys().leader();
    Assert.assertNotNull(leaderStatus);
    Assert.assertEquals(false, leaderStatus.getHAEnabled());
    Assert.assertEquals(false, leaderStatus.isSelf());
    Assert.assertEquals("", leaderStatus.getLeaderAddress());
  }

  @Test(expected = VaultException.class)
  public void leaderStatusBadRequest() throws IOException {
    VaultClient vaultClient = getVaultClient(400,
        Json.MEDIA_TYPE,
        "{\"errors\":[\"missing client token\"]}",
        "https://127.0.0.1:8200/v1/sys/leader"
    );

    vaultClient.getSys().leader();
  }

  @Test
  public void policy() throws IOException{
    VaultClient vaultClient = getVaultClient(200,
        Json.MEDIA_TYPE,
        "{\"policies\":[\"default\",\"root\"]}",
        "https://127.0.0.1:8200/v1/sys/policy"
    );

    List<String> expectedPolicies = Arrays.asList("default", "root");

    PolicyResponse policy = vaultClient.getSys().policies();
    Assert.assertNotNull(policy);
    Assert.assertNotNull(policy.getPolicies());
    Assert.assertThat(expectedPolicies, org.hamcrest.CoreMatchers.is(expectedPolicies));
  }

  @Test(expected = VaultException.class)
  public void policyBadRequest() throws IOException {
    VaultClient vaultClient = getVaultClient(400,
        Json.MEDIA_TYPE,
        "{\"errors\":[\"missing client token\"]}",
        "https://127.0.0.1:8200/v1/sys/policy"
    );

    vaultClient.getSys().policies();
  }
  
}
