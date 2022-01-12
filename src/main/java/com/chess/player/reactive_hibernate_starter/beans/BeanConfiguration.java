package com.chess.player.reactive_hibernate_starter.beans;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Factory
public class BeanConfiguration {

  @Singleton
  public EntityManagerFactory entityManagerFactory() {
    return Persistence.createEntityManagerFactory("customer-reactive-demo");
  }

  @Singleton
  public Stage.SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
    return entityManagerFactory.unwrap(Stage.SessionFactory.class);
  }
}
