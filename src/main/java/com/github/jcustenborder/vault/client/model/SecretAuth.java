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
package com.github.jcustenborder.vault.client.model;

import com.google.api.client.util.Key;

import java.util.List;
import java.util.Map;

public class SecretAuth {
  @Key("client_token")
  String clientToken;
  @Key("policies")
  List<String> policies;
  @Key("metadata")
  Map<String, Object> metadata;
  @Key("lease_duration")
  Integer leaseDuration;
  @Key("renewable")
  Boolean renewable;

  public Boolean getRenewable() {
    return renewable;
  }

  public Integer getLeaseDuration() {
    return leaseDuration;
  }

  public List<String> getPolicies() {
    return policies;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public String getClientToken() {
    return clientToken;
  }
}
