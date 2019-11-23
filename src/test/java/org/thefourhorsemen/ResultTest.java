package org.thefourhorsemen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.thefourhorsemen.FunctionAdapter.fromDeadEnd;
import static org.thefourhorsemen.FunctionAdapter.fromSingleTrack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {

  @Test
  void thenOnSuccess() {
    final String data = "some data in";
    Result.from(data)
          .then(fromSingleTrack(String::toUpperCase))
          .then(fromDeadEnd(System.out::println))
          .then(this::length)
          .onSuccess(v -> assertEquals(12, v))
          .onFailure(Assertions::fail);
  }

  @Test
  void thenOnFailure() {
    final String data = "";
    Result.from(data)
          .then(fromSingleTrack(String::toUpperCase))
          .then(this::length)
          .onSuccess(v -> fail("Should not be a success"))
          .onFailure(e -> assertEquals("empty string has no length", e));
  }

  private Result<Integer> length(final String input) {
    final int length = input.length();
    return length != 0 ? Result.success(length) : Result.failure("empty string has no length");
  }

}