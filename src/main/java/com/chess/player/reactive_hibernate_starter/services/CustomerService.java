package com.chess.player.reactive_hibernate_starter.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface CustomerService {

  static CustomerService create(Vertx vertx) {
    return new CustomerServiceImpl(vertx);
  }

  static CustomerService createProxy(Vertx vertx, String address) {
    return new CustomerServiceVertxEBProxy(vertx, address);
  }

  void getCustomers(Handler<AsyncResult<JsonObject>> handler);
}


