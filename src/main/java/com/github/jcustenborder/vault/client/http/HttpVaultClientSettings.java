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

public class HttpVaultClientSettings {
  String token;
  String url;
  boolean requestLoggingEnabled =false;
  boolean requestCurlLoggingEnabled =false;
  Integer numberOfRetries;

  HttpTransport httpTransport = new NetHttpTransport();


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

  void validate(){
    Preconditions.checkArgument(!Strings.isNullOrEmpty(this.url), "url cannot be null.");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(this.token), "token cannot be null.");
    if(null!=this.numberOfRetries){
      Preconditions.checkArgument(this.numberOfRetries>0, "numberOfRetries must be greater than 0.");
    }
  }

}
