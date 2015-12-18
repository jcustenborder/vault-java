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


import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public class HttpVaultClientSettings {
  String token;
  String url;
  boolean requestLoggingEnabled =false;
  boolean requestCurlLoggingEnabled =false;
  Integer numberOfRetries;
  boolean certificateValidationEnabled =true;
  Proxy proxy;
  KeyStore trustCertificates;

  HttpTransport mockHttpTransport;

  HttpTransport buildHttpTransport(){
    if(null!=this.mockHttpTransport){
      return this.mockHttpTransport;
    }

    NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
    //TODO: This is nasty figure out what we need to do with this general exception.
    //TODO: Log something here to really annoying if they don't use certificate validation.
    if(!this.certificateValidationEnabled){
      try {
        builder.doNotValidateCertificate();
      } catch (GeneralSecurityException e) {

      }
    }

    if(null!=proxy){
      builder.setProxy(proxy);
    }

    //TODO: This is nasty figure out what we need to do with this general exception.
    if(null!=trustCertificates){
      try {
        builder.trustCertificates(trustCertificates);
      } catch(GeneralSecurityException ex){

      }
    }
    return builder.build();
  }




  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isRequestCurlLoggingEnabled() {
    return requestCurlLoggingEnabled;
  }

  public void setRequestCurlLoggingEnabled(boolean requestCurlLoggingEnabled) {
    this.requestCurlLoggingEnabled = requestCurlLoggingEnabled;
  }

  public boolean isRequestLoggingEnabled() {
    return requestLoggingEnabled;
  }

  public void setRequestLoggingEnabled(boolean requestLoggingEnabled) {
    this.requestLoggingEnabled = requestLoggingEnabled;
  }

  public Integer getNumberOfRetries() {
    return numberOfRetries;
  }

  public void setNumberOfRetries(Integer numberOfRetries) {
    this.numberOfRetries = numberOfRetries;
  }

  public Proxy getProxy() {
    return proxy;
  }

  public void setProxy(Proxy proxy) {
    this.proxy = proxy;
  }

  public boolean isCertificateValidationEnabled() {
    return certificateValidationEnabled;
  }

  /**
   * This allows certificate validation to be disabled during testing. This should NEVER be used in production. The default is true.
   * Your better chance of not being lame is to add the certificate to a trust store and load it using setTrustCertificates.
   * @param certificateValidationEnabled
   */
  public void setCertificateValidationEnabled(boolean certificateValidationEnabled) {
    this.certificateValidationEnabled = certificateValidationEnabled;
  }

  /**
   * This method allows you to pass in a KeyStore that will be used to validate certificates in addition to the default java one.
   * @param trustCertificates
   */
  public void setTrustCertificates(KeyStore trustCertificates) {
    this.trustCertificates = trustCertificates;
  }

  public KeyStore getTrustCertificates() {
    return trustCertificates;
  }

  void validate(){
    Preconditions.checkArgument(!Strings.isNullOrEmpty(this.url), "url cannot be null.");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(this.token), "token cannot be null.");
    if(null!=this.numberOfRetries){
      Preconditions.checkArgument(this.numberOfRetries>0, "numberOfRetries must be greater than 0.");
    }
  }

}
