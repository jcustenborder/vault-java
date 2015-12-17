package com.github.jcustenborder.vault.client;

import com.github.jcustenborder.vault.client.model.Secret;

import java.io.IOException;
import java.util.Map;

public interface LogicalClient {
  /**
   * Delete the secret at the given path. If the secret does not exist, vault will still return true.
   * @param path The path to delete
   * @return true
   * @throws IOException
   */
  Boolean delete(String path) throws IOException;

  /**
   * Read the secret at the given path. If the secret does not exist, null will be returned.
   * @param path
   * @return
   * @throws IOException
   */
  Secret read(String path) throws IOException;

  /**
   * Write the secret at the given path with the given data. Note that the data must be a Hash!
   * @param path The path to write
   * @param data The data to write
   * @return
   * @throws IOException
   */
  Secret write(String path, Map<String, Object> data) throws IOException;
}
