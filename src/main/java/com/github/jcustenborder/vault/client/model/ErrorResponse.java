package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

import java.util.List;

public class ErrorResponse {
  @Key("errors")
  List<String> errors;

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
