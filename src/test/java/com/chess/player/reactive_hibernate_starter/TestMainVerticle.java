package com.chess.player.reactive_hibernate_starter;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle())
      .onSuccess(ok -> testContext.completeNow())
      .onFailure(failure -> testContext.failNow(failure));
  }

  @AfterEach
  public void tearDown(Vertx vertx, VertxTestContext testContext) {
    vertx.close()
      .onSuccess(ok -> testContext.completeNow())
      .onFailure(failure -> testContext.failNow(failure));
  }

  @Test
  void getCustomerReturnsAvalue(Vertx vertx, VertxTestContext testContext) throws Throwable {
    vertx.createHttpClient()
         .request(HttpMethod.GET, 8888,"127.0.0.1","/api/customer")
         .compose(request -> request.send())
         .compose(response -> response.body())
         .onSuccess(body -> testContext.verify(()->{
           assertEquals("{\"result\":[{\"name\":\"VERA MCCOY\",\"address\":\"1168 Najafabad Parkway\",\"zip\":\"40301\",\"city\":\"Kabul\",\"country\":\"Afghanistan\"},{\"name\":\"MARIO CHEATHAM\",\"address\":\"1924 Shimonoseki Drive\",\"zip\":\"52625\",\"city\":\"Batna\",\"country\":\"Algeria\"},{\"name\":\"JUDY GRAY\",\"address\":\"1031 Daugavpils Parkway\",\"zip\":\"59025\",\"city\":\"Bchar\",\"country\":\"Algeria\"},{\"name\":\"JUNE CARROLL\",\"address\":\"757 Rustenburg Avenue\",\"zip\":\"89668\",\"city\":\"Skikda\",\"country\":\"Algeria\"},{\"name\":\"ANTHONY SCHWAB\",\"address\":\"1892 Nabereznyje Telny Lane\",\"zip\":\"28396\",\"city\":\"Tafuna\",\"country\":\"American Samoa\"}]}", body.toString());
           testContext.completeNow();
         }))
         .onFailure(failure -> testContext.failNow(failure));
  }
}
