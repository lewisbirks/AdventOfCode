package com.lewisbirks.adventofcode.common.cache;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class CachedSupplierTest {

    @Test
    void cache() {
        Object o = new Object();
        final int[] counter = {0};
        Supplier<Object> memoize = CachedSupplier.memoize(() -> {
            counter[0]++;
            return o;
        });
        assertThat(memoize.get()).isEqualTo(o);
        assertThat(counter[0]).isEqualTo(1);
        assertThat(memoize.get()).isEqualTo(o);
        assertThat(counter[0]).isEqualTo(1);
    }
}
