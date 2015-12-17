package com.github.jcustenborder.vault.client;


public interface VaultClient {
  LogicalClient getLogical();
  SysClient getSys();
}
