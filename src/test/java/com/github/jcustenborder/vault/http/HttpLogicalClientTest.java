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
    final String path = "cubbyhole/asdfasdfas";
    ImmutableMap<String, Object> data = ImmutableMap.of("integer", (Object)0, "string", "asdfasd", "boolean", true);
    this.logicalClient.write(path, data);
//    Assert.assertNotNull(writtenSecret);
    Secret readSecret = this.logicalClient.read(path);
    Assert.assertNotNull(readSecret);




  }
}
