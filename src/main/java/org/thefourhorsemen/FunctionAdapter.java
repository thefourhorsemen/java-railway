package org.thefourhorsemen;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionAdapter {

  public static <T, U, E> Function<T, Result<U, E>> fromSingleTrack(final Function<T, U> function) {
    return value -> Result.success(function.apply(value));
  }

  public static <T, E> Function<T, Result<T, E>> fromDeadEnd(final Consumer<T> function) {
    return value -> {
      function.accept(value);
      return Result.success(value);
    };
  }

}
