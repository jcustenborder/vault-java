/**
 * Copyright 2015 Jeremy Custenborder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.vault.client.http;

import com.github.jcustenborder.vault.client.VaultClient;
import com.github.jcustenborder.vault.client.VaultClientBuilder;
import com.google.common.base.Preconditions;

import java.net.Proxy;
import java.security.KeyStore;

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

  /**
   * This allows certificate validation to be disabled during testing. This should NEVER be used in production. The default is true.
   * Your better chance of not being lame is to add the certificate to a trust store and load it using setTrustCertificates.
   * @param certificateValidationEnabled
   * @return
   */
  public HttpVaultClientBuilder withCertificateValidationEnabled(boolean certificateValidationEnabled){
    this.httpVaultClientSettings.setCertificateValidationEnabled(certificateValidationEnabled);
    return this;
  }

  public HttpVaultClientBuilder withProxy(Proxy proxy){
    this.httpVaultClientSettings.setProxy(proxy);
    return this;
  }

  public HttpVaultClientBuilder withTrustCertificates(KeyStore trustCertificates){
    this.httpVaultClientSettings.setTrustCertificates(trustCertificates);
    return this;
  }

  @Override
  public VaultClient build() {
    return new HttpVaultClient(this.httpVaultClientSettings);
  }
}
