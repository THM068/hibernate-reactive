package com.chess.player.reactive_hibernate_starter;

import static com.chess.player.reactive_hibernate_starter.constants.VertxSingletonHolder.*;

import static com.chess.player.reactive_hibernate_starter.constants.VertxSingletonHolder.getVertx;

import com.chess.player.reactive_hibernate_starter.verticles.CustomerRestVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Vertx vertx = getVertx();

    vertx.deployVerticle(CustomerRestVerticle.VERTICLE_NAME)
      .onSuccess(id -> this.handleSuccessfulDeployment(id, CustomerRestVerticle.class.getName(), startPromise ))
      .onFailure(throwable -> this.handleDeploymentFailure(throwable, startPromise));
  }

  private void handleDeploymentFailure(Throwable throwable, Promise<Void> startPromise) {
    logger.error("Deployment failure :{}", throwable);
    startPromise.complete();
  }

  private void handleSuccessfulDeployment(String deploymentId, String className, Promise<Void> startPromise) {
    logger.info("{} has been successful deployed with ref {}", className, deploymentId);
    startPromise.complete();
  }
}
