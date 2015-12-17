package com.github.jcustenborder.vault.client.http;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class HttpVaultClientSettings {
  String token;
  String url;
  boolean requestLoggingEnabled =false;
  boolean requestCurlLoggingEnabled =false;
  Integer numberOfRetries;

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
