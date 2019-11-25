package org.thefourhorsemen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.thefourhorsemen.FunctionAdapter.bind;
import static org.thefourhorsemen.FunctionAdapter.tee;

import org.junit.jupiter.api.Test;

public class ResultTest {

  enum ErrorMessage {
    EMPTY_STRING
  }

  @Test
  void thenOnSuccess() {
    final String data = "some data in";
    Result.from(data, ErrorMessage.class)
          .then(bind(String::toUpperCase))
          .then(tee(System.out::println))
          .then(this::length)
          .onSuccess(v -> assertEquals(12, v))
          .onFailure(e -> fail(e.toString()));
  }

  @Test
  void thenOnFailure() {
    final String data = "";
    Result.from(data, ErrorMessage.class)
          .then(bind(String::toUpperCase))
          .then(this::length)
          .onSuccess(v -> fail("Should not be a success"))
          .onFailure(e -> assertEquals(ErrorMessage.EMPTY_STRING, e));
  }

  private Result<Integer, ErrorMessage> length(final String input) {
    final int length = input.length();
    return length != 0 ? Result.success(length) : Result.failure(ErrorMessage.EMPTY_STRING);
  }

}