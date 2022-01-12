package com.chess.player.reactive_hibernate_starter.controllers;

import com.chess.player.reactive_hibernate_starter.MainVerticle;
import com.chess.player.reactive_hibernate_starter.dao.CustomerRepository;
import com.chess.player.reactive_hibernate_starter.model.Customer;
import com.chess.player.reactive_hibernate_starter.model.dto.FindAllRequest;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;
import org.hibernate.reactive.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CustomerController {
  private Logger logger = LoggerFactory.getLogger(CustomerController.class);

  private CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void attach(Router router) {
     router.route(HttpMethod.GET, "/api/customer").handler(this::getCustomers);
  }

  private void getCustomers(RoutingContext routingContext) {

    Future.fromCompletionStage(customerRepository.findAll(FindAllRequest.create()))
      .onSuccess(customerList -> {
      HttpServerResponse response = routingContext.response();
      List<JsonObject> customers = customerList.stream().map(customer -> {
        return new JsonObject()
          .put("name", customer.getName())
          .put("address", customer.getAddress())
          .put("zip", customer.getZip_code())
          .put("city", customer.getCity())
          .put("country", customer.getCountry());
      }).collect(Collectors.toList());
      response.putHeader("Content-Type", "application/json")
        .setStatusCode(200)
        .end(new JsonObject().put("result", new JsonArray(customers).encodePrettily()).toBuffer());
      logger.info("Getting all customers");
    }).
    onFailure(failure -> this.handleFailure(failure, routingContext));

  }

  private void handleFailure(Throwable failure, RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    response.setStatusCode(500)
    .end(new JsonObject().put("Error", "Database error").toBuffer());
  }


}
