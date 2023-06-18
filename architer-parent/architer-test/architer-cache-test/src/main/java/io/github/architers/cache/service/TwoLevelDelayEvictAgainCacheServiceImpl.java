package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.annotation.CachePut;
import io.github.architers.context.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author luyi
 */
@Service("twoLevelDelayEvictAgain")
public class TwoLevelDelayEvictAgainCacheServiceImpl implements ITwoLevelDelayEvictAgainCacheService {

    @Override
    @Cacheable(cacheName = "twoLevelDelayEvictAgain", key = "#username")
    public UserInfo getUserInfoWithCache(String username) {
        System.out.println("查询数据库");
        return new UserInfo().setUsername(username);
    }

    @Override
    @Cacheable(cacheName = "twoLevelDelayEvictAgain", key = "#username")
    public UserInfo getUserInfoOnlyCache(String username) {
        return null;
    }

    @Override
    @CacheEvict(cacheName = "twoLevelDelayEvictAgain", key = "#username")
    public void delete(String username) {
        System.out.println("删除:" + username);
    }

    @Override
    @CachePut(cacheName = "twoLevelDelayEvictAgain", key = "#userInfo.username", cacheValue = "#userInfo")
    public void updateUserInfo(UserInfo userInfo) {
        System.out.println("更新用户信息");
    }

    @Override
    @CacheEvict(cacheName = "twoLevelDelayEvictAgain", key = "#userInfo.username")
    public void updateUserInfoWithEvict(UserInfo userInfo) {

    }
}
