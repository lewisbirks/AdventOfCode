package com.lewisbirks.adventofcode.common.cache;

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
        if (cached == null) {
            cached = supplier.get();
        }
        return cached;
    }
}
