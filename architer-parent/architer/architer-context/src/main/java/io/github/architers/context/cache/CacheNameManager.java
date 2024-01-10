package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheNameWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheNameManager {

    private final Map<String, String> cacheNameMap = new ConcurrentHashMap<>();

    @Resource
    private CacheProperties cacheProperties;

    @Autowired(required = false)
    private CacheNameWrapper cacheNameWrapper;

    public String getWrapperCacheName(String cacheName) {
        String wrapperCacheName = cacheNameMap.get(cacheName);
        if (wrapperCacheName != null) {
            return wrapperCacheName;
        }
        StringBuilder cacheNameBuilder = new StringBuilder(10);
        if (StringUtils.hasText(cacheProperties.getNamespace())) {
            cacheNameBuilder.append(cacheProperties.getNamespace());
            cacheNameBuilder.append(":");
        }
        if (cacheNameWrapper != null) {
            cacheNameBuilder.append(cacheNameWrapper.getCacheName(null, cacheName));
        } else {
            cacheNameBuilder.append(cacheName);
        }
        cacheNameMap.putIfAbsent(cacheName, cacheNameBuilder.toString());
        return cacheNameMap.get(cacheName);
    }
}
