package com.chess.player.reactive_hibernate_starter.beans;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@ToString
public class BrokerConfig {
  private int server_port;

  public static BrokerConfig from(JsonObject jsonObject) {
      BrokerConfig brokerConfig = new BrokerConfig();
      brokerConfig.setServer_port(jsonObject.getInteger(ConfigLoader.SERVER_PORT));

      return brokerConfig;
  }

  public void setServer_port(int server_port) {
    this.server_port = server_port;
  }

  public int getServer_port() {
    return this.server_port;
  }
}
