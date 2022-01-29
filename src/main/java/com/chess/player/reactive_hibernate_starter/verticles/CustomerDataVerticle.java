package com.chess.player.reactive_hibernate_starter.verticles;

import com.chess.player.reactive_hibernate_starter.controllers.CustomerController;
import com.chess.player.reactive_hibernate_starter.services.CustomerService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDataVerticle extends AbstractVerticle {
  private Logger logger = LoggerFactory.getLogger(CustomerDataVerticle.class);
  public static final String VERTICLE_NAME = "com.chess.player.reactive_hibernate_starter.verticles.CustomerDataVerticle";

  @Override
  public void start(Promise<Void> promise) {
    logger.info("CustomerDataVerticle verticle .....");
    new ServiceBinder(vertx)
      .setAddress("customer.data")
        .register(CustomerService.class, CustomerService.create(vertx));

    promise.complete();
  }
}
