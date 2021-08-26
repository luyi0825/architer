package com.architecture.test.cache.mapvalue;

import com.architecture.context.cache.CacheMode;
import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.cache.annotation.DeleteCache;
import com.architecture.test.cache.UserInfo;

public interface MapValueService {
    String cacheName = "mapValue_user";

    @Cacheable(cacheName = "#root.target.cacheName", key = "#userName", expireTime = 2, cacheMode = CacheMode.MAP)
    UserInfo findByUserName(String userName);

    @DeleteCache(cacheName = "#root.target.cacheName", key = "#userName",cacheMode = CacheMode.MAP)
    void deleteByUserName(String userName);
}
