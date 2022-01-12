package com.chess.player.reactive_hibernate_starter.constants;

import io.vertx.core.Vertx;

public class VertxSingletonHolder {

  private static Vertx vertx;

  public static Vertx getVertx() {
    if(vertx == null) {
      vertx = Vertx.vertx();
    }

    return vertx;
  }
}
