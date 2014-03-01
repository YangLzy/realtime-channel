/*
 * Copyright 2014 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.goodow.realtime.channel.server;

import com.goodow.realtime.core.Net;
import com.goodow.realtime.core.Platform;
import com.goodow.realtime.core.Platform.Type;
import com.goodow.realtime.core.PlatformFactory;
import com.goodow.realtime.core.Scheduler;
import com.goodow.realtime.core.WebSocket;
import com.goodow.realtime.json.JsonObject;

import org.vertx.java.core.Vertx;

public class VertxPlatform implements PlatformFactory {
  /**
   * Registers the Vertx platform with a default configuration.
   */
  public static void register(Vertx vertx) {
    Platform.setFactory(new VertxPlatform(vertx));
  }

  protected final Scheduler scheduler;
  protected final Net net;

  private VertxPlatform(final Vertx vertx) {
    scheduler = new VertxScheduler(vertx);
    net = new Net() {
      @Override
      public WebSocket createWebSocket(String url, JsonObject options) {
        return new VertxWebSocket(vertx, url);
      }
    };
  }

  @Override
  public Net net() {
    return net;
  }

  @Override
  public Scheduler scheduler() {
    return scheduler;
  }

  @Override
  public Type type() {
    return Type.VERTX;
  }
}