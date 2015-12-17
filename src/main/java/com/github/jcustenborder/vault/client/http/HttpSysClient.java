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

import com.github.jcustenborder.vault.client.SysClient;
import com.github.jcustenborder.vault.client.model.LeaderStatus;
import com.github.jcustenborder.vault.client.model.Mount;
import com.github.jcustenborder.vault.client.model.PolicyResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.common.collect.ImmutableList;

import java.util.Map;

class HttpSysClient extends BaseHttpClient implements SysClient {
  public HttpSysClient(HttpRequestFactory httpRequestFactory, GenericUrl baseUrl, ImmutableList<String> basePathParts) {
    super(httpRequestFactory, baseUrl, basePathParts);
  }

  @Override
  public Map<String, Object> audits() {
    return null;
  }

  @Override
  public Map<String, Object> auths() {
    return null;
  }

  @Override
  public Object deletePolicy(String name) {
    return null;
  }

  @Override
  public Boolean disableAudit(String path) {
    return false;
  }

  @Override
  public Boolean disableAuth(String path) {
    return false;
  }

  @Override
  public Boolean enableAudit(String path, String type, String description, Map<String, Object> options) {
    return false;
  }

  @Override
  public Boolean enableAuth(String path, String type, String description) {
    return false;
  }

  @Override
  public Object init(Map<String, Object> options) {
    return null;
  }

  @Override
  public Object initStatus() {
    return null;
  }

  @Override
  public LeaderStatus leader() {
    return null;
  }

  @Override
  public Object mount(String path, String type, String description) {
    return null;
  }

  @Override
  public Map<String, Mount> mounts() {
    return null;
  }

  @Override
  public PolicyResponse policies() {
    return null;
  }

  @Override
  public Object readPolicy(String name) {
    return null;
  }

  @Override
  public Object writePolicy(String name, String rules) {
    return null;
  }

  @Override
  public Boolean remount(String from, String to) {
    return false;
  }

  @Override
  public Object renew(String id, int increment) {
    return null;
  }

  @Override
  public Boolean revoke(String id) {
    return false;
  }

  @Override
  public Boolean revoke_prefix(String id) {
    return false;
  }

  @Override
  public Boolean seal() {
    return false;
  }

  @Override
  public Object sealStatus() {
    return null;
  }

  @Override
  public Boolean unmount(String path) {
    return false;
  }

  @Override
  public Object unseal(String shard) {
    return null;
  }
}
