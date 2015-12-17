package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

import java.util.List;
import java.util.Map;

public class SecretAuth {
  @Key("client_token")
  String clientToken;
  @Key("policies")
  List<String> policies;
  @Key("metadata")
  Map<String, Object> metadata;
  @Key("lease_duration")
  Integer leaseDuration;
  @Key("renewable")
  Boolean renewable;

  public Boolean getRenewable() {
    return renewable;
  }

  public Integer getLeaseDuration() {
    return leaseDuration;
  }

  public List<String> getPolicies() {
    return policies;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public String getClientToken() {
    return clientToken;
  }
}
