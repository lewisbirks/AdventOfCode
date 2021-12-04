package git.lewisbirks.adventofcode.common;

import java.util.Objects;
import java.util.function.Supplier;

public class CachedSupplier<T> implements Supplier<T> {

  private final Supplier<T> supplier;
  private T cached;

  private CachedSupplier(Supplier<T> supplier) {
    this.supplier = supplier;
    this.cached = null;
  }

  public static <T> Supplier<T> memoize(final Supplier<T> supplier) {
    return new CachedSupplier<>(supplier);
  }

  @Override
  public T get() {
    cached = Objects.requireNonNullElseGet(cached, supplier);
    return cached;
  }
}
