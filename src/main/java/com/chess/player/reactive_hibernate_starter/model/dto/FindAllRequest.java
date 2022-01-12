package com.chess.player.reactive_hibernate_starter.model.dto;


public class FindAllRequest {

  private int numberOfResults = 5;
  private FindAllRequest() {}

  public static FindAllRequest create() {
    return new FindAllRequest();
  }

  public int getNumberOfResults() {
    return numberOfResults;
  }

  public void setNumberOfResults(int numberOfResults) {
    this.numberOfResults = numberOfResults;
  }

}
