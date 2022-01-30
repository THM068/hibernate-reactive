package com.chess.player.reactive_hibernate_starter.verticles;

import com.chess.player.reactive_hibernate_starter.controllers.CustomerController;
import com.chess.player.reactive_hibernate_starter.services.CustomerService;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;

public class CustomerRestVerticle extends AbstractVerticle  {
  public static String VERTICLE_NAME = "com.chess.player.reactive_hibernate_starter.verticles.CustomerRestVerticle";
  private Logger logger = LoggerFactory.getLogger(CustomerRestVerticle.class);
  private static int API_PORT = 8888;
  private CustomerController customerController;

  public CustomerRestVerticle() {
    BeanContext beanContext = BeanContext.run();
    this.customerController = beanContext.getBean(CustomerController.class);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Vertx vertx = getVertx();
    Router router = Router.router(getVertx());
    router.route().handler(LoggerHandler.create());
    applyBodyHander(router);

    this.customerController.attach(router);

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(API_PORT)
      .onSuccess(this::successServerStartup)
      .onFailure(this::handleServerfailure);

    startPromise.complete();
  }

  private void handleServerfailure(Throwable throwable) {
    logger.error("failed to start server {}", throwable);
  }

  private void successServerStartup(HttpServer httpServer) {
    logger.info("HTTP server started on port {}", httpServer.actualPort());
  }

  private void applyBodyHander(Router router) {
    BodyHandler bodyHandler = BodyHandler.create();
    router.route().method(POST).handler(bodyHandler);
    router.route().method(PUT).handler(bodyHandler);
  }
}
