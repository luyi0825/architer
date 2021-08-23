package com.architecture.test.cache.put;

import com.architecture.context.cache.annotation.PutCache;
import com.architecture.test.cache.UserInfo;

public interface PutService {
    @PutCache(cacheName = "'onePut'", key = "#userInfo.username", cacheValue = "#userInfo")
    void onePut(UserInfo userInfo);

    @PutCache(cacheName = "'twoPut'", key = "#userInfo.username+'_1'", cacheValue = "#userInfo")
    @PutCache(cacheName = "'twoPut'", key = "#userInfo.username+'_2'", cacheValue = "#userInfo")
    void twoPut(UserInfo userInfo);

    @PutCache(cacheName = "'returnValue'", key = "#userInfo.username", cacheValue = "#result")
    UserInfo returnValue(UserInfo userInfo);
}
