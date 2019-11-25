package org.thefourhorsemen;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionAdapter {

  /**
   * Returns a two-track function from a single-track function, i.e. a function that never fails.
   */
  public static <T, U, E> Function<T, Result<U, E>> bind(final Function<T, U> function) {
    return value -> Result.success(function.apply(value));
  }

  /**
   * Returns a two-track function from a dead-end function, i.e. a function that returns nothing.
   */
  public static <T, E> Function<T, Result<T, E>> tee(final Consumer<T> function) {
    return value -> {
      function.accept(value);
      return Result.success(value);
    };
  }

}
