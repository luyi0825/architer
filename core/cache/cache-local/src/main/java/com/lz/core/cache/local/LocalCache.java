package com.lz.core.cache.local;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：当地缓存
 *
 * @author luyi
 * @date 2021/3/10
 */
public class LocalCache extends ConcurrentHashMap<String, Object> implements Serializable {

    public LocalCache() {
    }

    public LocalCache(int initialCapacity) {
        super(initialCapacity);
    }

    public LocalCache(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public LocalCache(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public LocalCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
        super(initialCapacity, loadFactor, concurrencyLevel);
    }
}
