package com.example.datecalculator.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class EntityCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private static final int CAPACITY = 200;

    public void put(final K key, final V value) {
        if (cache.size() >= CAPACITY) {
            cache.clear();
        }
        cache.put(key, value);
    }

    public V get(final K key) {
        return cache.get(key);
    }

    public void remove(final K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }
}
