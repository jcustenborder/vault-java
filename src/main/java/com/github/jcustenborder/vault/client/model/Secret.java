package com.github.jcustenborder.vault.client.model;


import com.google.api.client.util.Key;

import java.util.Map;

public class Secret {
  @Key("lease_id")
  String leaseID;
  @Key("renewable")
  Boolean renewable;
  @Key("data")
  Map<String, Object> data;

  @Key("auth")
  SecretAuth auth;

  public Boolean getRenewable() {
    return renewable;
  }

  public String getLeaseID() {
    return leaseID;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public SecretAuth getAuth() {
    return auth;
  }
}
