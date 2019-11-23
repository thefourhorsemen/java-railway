package org.thefourhorsemen;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionAdapter {

  public static <T, U> Function<T, Result<U>> fromSingleTrack(final Function<T, U> function) {
    return value -> Result.success(function.apply(value));
  }

  public static <T> Function<T, Result<T>> fromDeadEnd(final Consumer<T> function) {
    return value -> {
      function.accept(value);
      return Result.success(value);
    };
  }

}
