package org.thefourhorsemen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.thefourhorsemen.FunctionAdapter.fromDeadEnd;
import static org.thefourhorsemen.FunctionAdapter.fromSingleTrak;

import org.junit.jupiter.api.Test;

public class ResultTest {

  @Test
  void then() {
    final String data = "some data in";
    Result.success(data)
          .then(fromSingleTrak(String::toUpperCase))
          .then(fromDeadEnd(System.out::println))
          .then(this::length)
          .then(v -> assertValue(12, v));
  }

  @Test
  void otherwise() {
    final String data = "";
    Result.success(data)
          .then(fromSingleTrak(String::toUpperCase))
          .then(this::length)
          .then(v -> assertValue(12, v))
          .otherwise(e -> assertValue("empty string has no length", e));
  }

  private void assertValue(final String expected, final String actual) {
    assertEquals(expected, actual);
  }

  private Result<Object> assertValue(final int expected, final int actual) {
    assertEquals(expected, actual);
    return null;
  }

  private Result<Integer> length(final String input) {
    final int length = input.length();
    return length != 0 ? Result.success(length) : Result.failure("empty string has no length");
  }

}