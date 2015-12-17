package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

public class Mount {
  @Key("description")
  String description;
  @Key("type")
  String type;
  @Key("config")
  MountConfig config;

  public MountConfig getConfig() {
    return config;
  }

  public String getDescription() {
    return description;
  }

  public String getType() {
    return type;
  }
}
