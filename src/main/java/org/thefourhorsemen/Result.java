package org.thefourhorsemen;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T, E> {

  private final T value;
  private final E errorMessage;

  private Result(final T value, final E errorMessage) {
    this.value = value;
    this.errorMessage = errorMessage;
  }

  public static <T, E> Result<T, E> from(final T value, final Class<E> clazz) {
    return success(value);
  }

  public static <T, E> Result<T, E> success(final T value) {
    return new Result<>(Objects.requireNonNull(value), null);
  }

  public static <T, E> Result<T, E> failure(final E errorMessage) {
    return new Result<>(null, Objects.requireNonNull(errorMessage));
  }

  public <U> Result<U, E> then(final Function<T, Result<U, E>> function) {
    return success() ? function.apply(value) : Result.failure(errorMessage);
  }

  public Result<T, E> onSuccess(final Consumer<T> consumer) {
    if (success()) {
      consumer.accept(value);
    }
    return this;
  }

  public Result<T, E> onFailure(final Consumer<E> consumer) {
    if (!success()) {
      consumer.accept(errorMessage);
    }
    return this;
  }

  private boolean success() {
    return Objects.isNull(errorMessage);
  }
}
