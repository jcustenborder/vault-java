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

public class LeaderStatus {
  @Key("ha_enabled")
  Boolean HAEnabled;
  @Key("is_self")
  Boolean isSelf;
  @Key("leader_address")
  String leaderAddress;

  public Boolean getHAEnabled() {
    return HAEnabled;
  }

  public Boolean getSelf() {
    return isSelf;
  }

  public String getLeaderAddress() {
    return leaderAddress;
  }
}
