package com.chess.player.reactive_hibernate_starter;

import org.junit.jupiter.api.Test;

public class JavaRecursion {

  @Test
  public void simple_recursion() {
    countDown(10);

    int result = factorial(5);
    System.out.println(result);
  }

  private void countDown(int num) {
    if(num < 0) {
      return;
    }
    System.out.println(num);
    countDown(num -1);
  }

  private int factorial(int num) {
    if(num == 1) {
      return 1;
    }
    else
      return num * factorial(num -1);
  }
}
