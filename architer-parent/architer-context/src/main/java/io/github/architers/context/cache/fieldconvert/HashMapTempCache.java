package io.github.architers.context.cache.fieldconvert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * hashMap对应的临时缓存
 */
public class HashMapTempCache implements TempCache {

    private final HashMap<String, Object> value;

    public HashMapTempCache(int initialCapacity, float loadFactor) {
        value = new HashMap<>(initialCapacity, loadFactor);
    }

    public HashMapTempCache(int initialCapacity) {
        value = new HashMap<>(initialCapacity);
    }

    public HashMapTempCache() {
        value = new HashMap<>(16);
    }

    public HashMapTempCache(Map<? extends String, ? extends Serializable> map) {
        value = new HashMap<>(map);
    }

    @Override
    public boolean isCanEvict() {
        return false;
    }

    @Override
    public Object get(String key) {
        return value.get(key);
    }

    @Override
    public void put(String key, Object data) {
        value.put(key, data);
    }

    @Override
    public void batchPut(Map<String, Object> dataMap) {
        value.putAll(dataMap);
    }

    @Override
    public boolean containsKey(String key) {
        return value.containsKey(key);
    }

}
