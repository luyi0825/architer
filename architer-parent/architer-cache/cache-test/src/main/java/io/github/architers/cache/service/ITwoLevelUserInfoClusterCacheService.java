package io.github.architers.cache.service;


import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CacheEvict;

public interface ITwoLevelUserInfoClusterCacheService {

    /**
     * 获取用户信息
     */
    @Cacheable(cacheName = "twoLevelCluster", key = "#username")
    UserInfo getUserInfoWithCache(String username);

    @CacheEvict(cacheName = "twoLevelCluster",key = "#username")
    void delete(String username);
}
