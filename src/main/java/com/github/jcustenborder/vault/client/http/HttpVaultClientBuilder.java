package com.github.jcustenborder.vault.client.http;

import com.github.jcustenborder.vault.client.VaultClient;
import com.github.jcustenborder.vault.client.VaultClientBuilder;
import com.google.common.base.Preconditions;

public class HttpVaultClientBuilder implements VaultClientBuilder {
  final HttpVaultClientSettings httpVaultClientSettings;

  public HttpVaultClientBuilder(){
    this(new HttpVaultClientSettings());
  }

  public HttpVaultClientBuilder(HttpVaultClientSettings httpVaultClientSettings) {
    Preconditions.checkNotNull(httpVaultClientSettings, "httpVaultClientSettings cannot be null");
    this.httpVaultClientSettings = httpVaultClientSettings;
  }

  public HttpVaultClientBuilder withToken(String token){
    this.httpVaultClientSettings.setToken(token);
    return this;
  }

  public HttpVaultClientBuilder withUrl(String url){
    this.httpVaultClientSettings.setUrl(url);
    return this;
  }

  public HttpVaultClientBuilder withEnableRequestLogging(boolean enableRequestLogging){
    this.httpVaultClientSettings.setRequestLoggingEnabled(enableRequestLogging);
    return this;
  }

  public HttpVaultClientBuilder withEnableCurlRequestLogging(boolean enableCurlRequestLogging){
    this.httpVaultClientSettings.setRequestCurlLoggingEnabled(enableCurlRequestLogging);
    return this;
  }

  public HttpVaultClientBuilder withNumberOfRetries(Integer numberOfRetries){
    this.httpVaultClientSettings.setNumberOfRetries(numberOfRetries);
    return this;
  }


  @Override
  public VaultClient build() {
    return new HttpVaultClient(this.httpVaultClientSettings);
  }
}
