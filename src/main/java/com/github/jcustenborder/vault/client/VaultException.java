package com.github.jcustenborder.vault.client;


import com.google.api.client.repackaged.com.google.common.base.Joiner;

import java.util.List;

public class VaultException extends RuntimeException {
  final String[] errors;

  public VaultException(String[] errors) {
    this.errors = errors;
  }
  public VaultException(List<String> errors) {
    this(errors.toArray(new String[errors.size()]));
  }

  @Override
  public String getMessage() {
    return String.format(
        "The following errors were returned from vault:%s",
        Joiner.on("\n").join(errors)
    );
  }

  public String[] getErrors() {
    return errors;
  }
}
