package io.github.architers.context.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheOperateConfigContextManager {

    private static final Map<String, CacheConfigContext> cacheConfigContextMap = new ConcurrentHashMap<>();

    private static volatile CacheConfigContext defaultCacheConfigContext;

    public static void setDefaultCacheConfigContext(CacheConfigContext defaultCacheConfigContext) {
        CacheOperateConfigContextManager.defaultCacheConfigContext = defaultCacheConfigContext;
    }

    public static CacheConfigContext getCacheConfigContext(String cacheName) {
        CacheConfigContext cacheConfigContext = cacheConfigContextMap.get(cacheName);
        if (cacheConfigContext != null) {
            return cacheConfigContext;
        }
        return defaultCacheConfigContext;
    }

    public static void updateConfigContext(String cacheName, CacheConfigContext cacheConfigContext) {
        cacheConfigContextMap.put(cacheName, cacheConfigContext);
    }

}
