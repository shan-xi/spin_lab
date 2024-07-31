package org.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxEntries;

    public LRUCache(int maxEntries) {
        super(maxEntries, 0.75f, true);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        System.out.println("Initial cache: " + cache);

        // Access some elements
        cache.get(1);
        cache.get(2);
        System.out.println("Cache after accessing elements: " + cache);

        // Add another element, this should evict the least recently used (key=3)
        cache.put(4, "Four");
        System.out.println("Cache after adding one more element: " + cache);

        // Add another element, this should evict the least recently used (key=1)
        cache.put(5, "Five");
        System.out.println("Cache after adding another element: " + cache);
    }
}
