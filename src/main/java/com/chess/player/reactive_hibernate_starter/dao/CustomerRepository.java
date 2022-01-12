package com.chess.player.reactive_hibernate_starter.dao;

import com.chess.player.reactive_hibernate_starter.model.Customer;
import com.chess.player.reactive_hibernate_starter.model.dto.FindAllRequest;
import jakarta.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

import java.util.List;
import java.util.concurrent.CompletionStage;

@Singleton
public class CustomerRepository implements Repository<Customer> {

  private Stage.SessionFactory sessionFactory;

  public CustomerRepository(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public CompletionStage<List<Customer>> findAll(FindAllRequest findAllRequest) {
    return sessionFactory.withSession(session -> session.createQuery("select a from customer_list a",Customer.class)
      .setMaxResults(findAllRequest.getNumberOfResults())
      .getResultList());
  }
}
