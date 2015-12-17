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
package com.github.jcustenborder.vault.client;

import com.github.jcustenborder.vault.client.model.Auth;
import com.github.jcustenborder.vault.client.model.LeaderStatus;
import com.github.jcustenborder.vault.client.model.Mount;
import com.github.jcustenborder.vault.client.model.PolicyResponse;

import java.io.IOException;
import java.util.Map;

public interface SysClient {
  /**
   * List all audits for the vault.
   * @return
   */
  Map<String, Object> audits();
  /**
   * List all auths in Vault.
   * @return
   */
  Map<String, Auth> auths() throws IOException;
  /**
   * Delete the policy with the given name.
   * @param name
   * @return
   */
  Object deletePolicy(String name);
  /**
   * Disable a particular audit.
   * @param path
   * @return
   */
  Boolean disableAudit(String path);
  /**
   * Disable a particular authentication at the given path.
   * @param path
   * @return
   */
  Boolean disableAuth(String path);
  /**
   * Enable a particular audit.
   * @param path
   * @param type
   * @param description
   * @param options
   * @return
   */
  Boolean enableAudit(String path, String type, String description, Map<String, Object> options);
  /**
   * Enable a particular authentication at the given path.
   * @param path
   * @param type
   * @param description
   * @return
   */
  Boolean enableAuth(String path, String type, String description);
  /**
   * Initialize a new vault.
   * @param options
   * @return
   */
  Object init(Map<String, Object> options);
  /**
   * Show the initialization status for this vault.
   * @return
   */
  Object initStatus();

  /**
   * Determine the leader status for this vault.
   * @return
   */
  LeaderStatus leader();

  /**
   * Create a mount at the given path.
   * @param path
   * @param type
   * @param description
   * @return
   */
  Object mount(String path, String type, String description);

  /**
   * List all mounts in the vault.
   * @return
   */
  Map<String, Mount> mounts();

  /**
   * The list of policies in vault.
   * @return
   */
  PolicyResponse policies();

  /**
   * Get the policy by the given name.
   * @param name
   * @return
   */
  Object readPolicy(String name);

  /**
   * Create a new policy with the given name and rules.
   * @param name
   * @param rules
   * @return
   */
  Object writePolicy(String name, String rules);

  /**
   * Change the name of the mount.
   * @param from
   * @param to
   * @return
   */
  Boolean remount(String from, String to);

  /**
   * Renew a lease with the given ID.
   * @param id
   * @param increment
   * @return
   */
  Object renew(String id, int increment);

  /**
   * Revoke the secret at the given id.
   * @param id
   * @return
   */
  Boolean revoke(String id);

  /**
   * Revoke all secrets under the given prefix.
   * @param id
   * @return
   */
  Boolean revoke_prefix(String id);

  /**
   * Seal the vault.
   * @return
   */
  Boolean seal();

  /**
   * Get the current seal status.
   * @return
   */
  Object sealStatus();

  /**
   * Unmount the thing at the given path.
   * @param path
   * @return
   */
  Boolean unmount(String path);

  /**
   * Unseal the vault with the given shard.
   * @param shard
   * @return
   */
  Object unseal(String shard);
}
