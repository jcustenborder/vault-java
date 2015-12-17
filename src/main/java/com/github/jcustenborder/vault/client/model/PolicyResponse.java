package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

import java.util.List;

public class PolicyResponse {
  @Key("policies")
  List<String> policies;

  public List<String> getPolicies() {
    return policies;
  }
}
