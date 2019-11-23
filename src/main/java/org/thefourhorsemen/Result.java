package org.thefourhorsemen;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T> {

  private final T value;
  private final String errorMessage;

  private Result(final T value, final String errorMessage) {
    this.value = value;
    this.errorMessage = errorMessage;
  }

  public static <T> Result<T> success(final T value) {
    return new Result(value, null);
  }

  public static <T> Result<T> failure(final String errorMessage) {
    return new Result(null, errorMessage);
  }

  public <U> Result<U> then(final Function<T, Result<U>> function) {
    return success() ? function.apply(value) : Result.failure(errorMessage);
  }

  public void otherwise(final Consumer<String> consumer) {
    if (!success()) {
      consumer.accept(errorMessage);
    }
  }

  private boolean success() {
    return Objects.isNull(errorMessage);
  }
}
