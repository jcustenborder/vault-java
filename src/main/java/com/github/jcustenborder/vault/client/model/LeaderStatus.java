package com.github.jcustenborder.vault.client.model;


import com.google.api.client.util.Key;

public class LeaderStatus {
  @Key("ha_enabled")
  Boolean HAEnabled;
  @Key("is_self")
  Boolean isSelf;
  @Key("leader_address")
  String leaderAddress;

  public Boolean getHAEnabled() {
    return HAEnabled;
  }

  public Boolean getSelf() {
    return isSelf;
  }

  public String getLeaderAddress() {
    return leaderAddress;
  }
}
