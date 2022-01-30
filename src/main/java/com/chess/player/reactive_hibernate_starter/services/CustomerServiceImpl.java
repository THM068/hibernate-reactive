package com.chess.player.reactive_hibernate_starter.services;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class CustomerServiceImpl implements CustomerService {

  private final  Vertx vertx;

  public  CustomerServiceImpl(Vertx vertx) {
    this.vertx = vertx;
  }
  @Override
  public void getCustomers(Handler<AsyncResult<JsonObject>> handler) {
    JsonObject jsonObject = new JsonObject().put("message", "hello-you");
    handler.handle(Future.succeededFuture(jsonObject));
  }

  private void handleGetCustomer(Message<JsonObject> message, Handler<AsyncResult<JsonObject>> handler) {
      if(message != null) {
        System.out.println(message.body().toString());
        JsonObject jsonObject = new JsonObject().put("message", "hello-you");
        handler.handle(Future.succeededFuture(jsonObject));
      }
      else {
        handler.handle(Future.failedFuture("No value has been observed for "));
      }

  }
}
