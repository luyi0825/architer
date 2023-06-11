package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.PutCache;

import java.util.concurrent.TimeUnit;

public interface IUserService {

    /**
     * 保存不过期
     */
    @PutCache(cacheName = "user", key = "#userInfo.username", cacheValue = "#userInfo")
    void saveNotExpired(UserInfo userInfo);

    /**
     * 保存五秒过期
     */
    @PutCache(cacheName = "user", key = "#userInfo.username", cacheValue = "#userInfo", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    void saveExpiredWithFiveSecond(UserInfo userInfo);

    @PutCache(cacheName = "user", key = "#userInfo.username", cacheValue = "#userInfo", expireTime = 5)
    void saveExpiredWithFiveMinute(UserInfo userInfo);

    /**
     * 从缓存中查询
     */
    @Cacheable(cacheName = "user", key = "#username")
    UserInfo findByUsernameByCache(String username);

    /**
     * 没有就查询数据库
     */
    @Cacheable(cacheName = "user", key = "#username")
    UserInfo findByUsername(String username);

    @Cacheable(cacheName = "user", key = "#username", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    UserInfo findByUsernameExpiredWithFiveSecond(String username);


}
