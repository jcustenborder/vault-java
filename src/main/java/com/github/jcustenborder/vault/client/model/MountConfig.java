package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

public class MountConfig {
  @Key("default_lease_ttl")
  Integer defaultLeaseTTL;
  @Key("max_lease_ttl")
  Integer maxLeaseTTL;

  public Integer getDefaultLeaseTTL() {
    return defaultLeaseTTL;
  }
  public Integer getMaxLeaseTTL() {
    return maxLeaseTTL;
  }
}
