package com.lewisbirks.adventofcode.collection;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class FrequencyMap<K> extends HashMap<K, Long> {

    public FrequencyMap() {
    }

    public static <K> Collector<K, ?, FrequencyMap<K>> collector() {
        return Collectors.toMap(Function.identity(), o -> 1L, Long::sum, FrequencyMap::new);
    }

    public Long put(K key) {
        return put(key, 1L);
    }

    @Override
    public Long put(K key, Long value) {
        return super.merge(key, value, Long::sum);
    }

    @Override
    public Long get(Object key) {
        Long value = super.get(key);
        return value == null ? 0L : value;
    }

    public LongStream valueStream() {
        return super.values().stream().mapToLong(Long::longValue);
    }
}
