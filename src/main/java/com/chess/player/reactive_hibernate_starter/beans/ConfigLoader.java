package com.chess.player.reactive_hibernate_starter.beans;

import com.chess.player.reactive_hibernate_starter.verticles.CustomerDataVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Singleton
public class ConfigLoader {

  public static final String SERVER_PORT = "SERVER_PORT";
  private static List<String> EXPOSED_KEYS = Arrays.asList(SERVER_PORT);
  private Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

  public Future<BrokerConfig> load(Vertx vertx) {
    final JsonArray exposedKeys = new JsonArray();
    EXPOSED_KEYS.forEach(exposedKeys::add);

    ConfigStoreOptions envStore = new ConfigStoreOptions()
      .setType("env").setConfig(new JsonObject().put("keys", exposedKeys));

    ConfigRetriever configRetriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(envStore));

    return configRetriever.getConfig().map(BrokerConfig::from);
  }
}
