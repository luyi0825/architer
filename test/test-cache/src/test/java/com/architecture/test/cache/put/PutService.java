package com.architecture.test.cache.put;

import com.architecture.context.cache.annotation.PutCache;
import com.architecture.test.cache.UserInfo;

public interface PutService {
    @PutCache(cacheName = "'onePut'", key = "#userInfo.username", cacheValue = "#userInfo")
    void onePut(UserInfo userInfo);
}
