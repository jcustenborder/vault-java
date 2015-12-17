package com.github.jcustenborder.vault.client;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class HttpVaultClient implements VaultClient {
  private final HttpRequestFactory httpRequestFactory;
  private final HttpHeaders httpHeaders;
  private final HttpTransport httpTransport;
  private final JsonFactory jsonFactory = new GsonFactory();
  private final String USER_AGENT = "VaultJava/0.0.0 (+github.com/jcustenborder/vault-java)";
  private final LogicalClient logicalClient;
  private final SysClient sysClient;

  public HttpVaultClient(HttpTransport httpTransport, String url, String token){
    Preconditions.checkNotNull(httpTransport, "httpTransport cannot be null.");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "url cannot be null.");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(token), "token cannot be null.");
    this.httpTransport = httpTransport;
    this.httpHeaders = new HttpHeaders();
    this.httpHeaders.set("X-Vault-Token", token);
    this.httpHeaders.setUserAgent(USER_AGENT);
    this.httpRequestFactory = this.httpTransport.createRequestFactory(
        new HttpRequestInitializer() {
          @Override
          public void initialize(HttpRequest request) throws IOException {
            request.setParser(new JsonObjectParser(jsonFactory));
            request.setHeaders(httpHeaders);
            request.setLoggingEnabled(true);
            request.setCurlLoggingEnabled(true);
          }
        }
    );
    GenericUrl baseUrl = new GenericUrl(URI.create(url));
    List<String> pathParts = baseUrl.getPathParts();
    List<String> clean = new ArrayList<>();
    for(String s:pathParts){
      if(!Strings.isNullOrEmpty(s))
        clean.add(s);
    }

    ImmutableList<String> basePathParts = ImmutableList.copyOf(clean);
    this.logicalClient = new HttpLogicalClient(this.httpRequestFactory, baseUrl, basePathParts);
    this.sysClient = new HttpSysClient(this.httpRequestFactory, baseUrl, basePathParts);
  }

  public HttpVaultClient(String url, String token){
    this(new NetHttpTransport(), url, token);
  }

  @Override
  public LogicalClient getLogical() {
    return this.logicalClient;
  }

  @Override
  public SysClient getSys() {
    return this.sysClient;
  }
}
