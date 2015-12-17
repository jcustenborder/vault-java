package com.github.jcustenborder.vault.client.model;


import com.google.api.client.util.Key;

public class InitStatus {
  @Key("initialized")
  Boolean initialized;

  public Boolean getInitialized() {
    return initialized;
  }
}
