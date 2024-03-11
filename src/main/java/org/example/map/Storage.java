package org.example.map;

import java.util.HashMap;

public class Storage {
    private final HashMap<String, String> data;

    public Storage() {
        this.data = new HashMap<>();
    }

    public synchronized void put(String key, String value) {
        data.put(key, value);
    }

    public synchronized void remove(String key) {
        data.remove(key);
    }

    public synchronized String get(String key) {
        return data.get(key);
    }
}
