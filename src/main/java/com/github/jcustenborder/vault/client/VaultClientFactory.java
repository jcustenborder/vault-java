package com.github.jcustenborder.vault.client;


public class VaultClientFactory {
  final String token;
  final String url;

  public VaultClientFactory(String token, String url){
    this.token = token;
    this.url = url;
  }

  public VaultClient createVaultClient(){
    return new HttpVaultClient(this.url, this.token);
  }
}
