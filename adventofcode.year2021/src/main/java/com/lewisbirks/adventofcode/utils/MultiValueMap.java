package com.lewisbirks.adventofcode.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValueMap<K, V> {
    private Map<K, List<V>> map;

    public MultiValueMap() {
        this.map = new HashMap<>();
    }

    public void put(K key, V value) {
        List<V> values = map.getOrDefault(key, new ArrayList<>());
        values.add(value);
        map.put(key, values);
    }

    public List<V> get(K key) {
        return map.get(key);
    }

    public MultiValueMap<K, V> immutable() {
        map = Collections.unmodifiableMap(map);
        return this;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
