package io.github.architers.cache.put;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CachePut;

public interface PutService {
    @CachePut(cacheName = "onePut", key = "#userInfo.username", cacheValue = "#userInfo")
    void onePut(UserInfo userInfo);

    @Cacheable(cacheName = "onePut", key = "#username")
    UserInfo getUserInfo(String username);

    @CachePut(cacheName = "twoPut", key = "#userInfo.username+'_1'", cacheValue = "#userInfo")
    @CachePut(cacheName = "twoPut", key = "#userInfo.username+'_2'", cacheValue = "#userInfo")
    void twoPut(UserInfo userInfo);

    @CachePut(cacheName = "returnValue", key = "#userInfo.username", cacheValue = "#result")
    UserInfo returnValue(UserInfo userInfo);

    @CachePut(cacheName = "putCache_expireTime", key = "#userInfo.username", cacheValue = "#userInfo.username",
            expireTime = 2)
    void expireTime(UserInfo userInfo);

    @CachePut(cacheName = "putCache_randomTime", key = "#userInfo.username", cacheValue = "#userInfo",
            expireTime = 2, randomTime = 2)
    void randomTime(UserInfo userInfo);

    @CachePut(cacheName = "putCache_condition", key = "#userInfo.username+'_1'", cacheValue = "#result", condition = "#result.username.startsWith('666')")
    UserInfo condition(UserInfo userInfo);

    @CachePut(cacheName = "'putCache_unless'", key = "#userInfo.username+'_1'", cacheValue = "#result", unless = "#result.username.startsWith('666')")
    UserInfo unless(UserInfo userInfo);
}
