package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.CacheBatchPut;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("twoLevelCacheUserInfoCache")
public class TwoLevelUserInfoCacheServiceImpl implements ITwoLevelUserInfoCacheService {
    /**
     *  放置本地缓存永远不过期
     */
    @Override
    @CachePut(cacheName = "twoLevel", key = "#userInfo.username", cacheValue = "#userInfo")
    public void putCacheNeverExpire(UserInfo userInfo) {

    }

    /**
     * 测试本地缓存过期五秒
     */
    @Override
    @CachePut(cacheName = "twoLevel", key = "#userInfo.username", cacheValue = "#userInfo", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    public void putCacheExpireWithFiveSecond(UserInfo userInfo) {

    }

    /**
     * 仅从缓存中获取
     */
    @Override
    @Cacheable(cacheName = "twoLevel", key = "#username")
    public UserInfo getOnlyInCache(String username) {
        return null;
    }

    @Override
    @Cacheable(cacheName = "twoLevel", key = "#username")
    public UserInfo getNeverExpire(String username) {
        System.out.println("getNeverExpire查询");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }

    @Override
    @Cacheable(cacheName = "twoLevel", key = "#username", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    public UserInfo getExpireWithFiveSecond(String username) {
        System.out.println("getExpireWithFiveSecond查询");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }

    /**
     * 删除缓存
     */
    @Override
    @CacheEvict(cacheName = "twoLevel", key = "#username")
    public void delete(String username) {

    }

    /**
     * 集合批量put不过期
     */
    @Override
    @CacheBatchPut(cacheName = "twoLevel", cacheValue = "#userInfos")
    public void collectionBatchPutNeverExpire(List<UserInfo> userInfos) {

    }

    /**
     * map批量put不过期
     */
    @Override
    @CacheBatchPut(cacheName = "twoLevel", cacheValue = "#userMap")
    public void mapBatchPutNeverExpire(Map<String, UserInfo> userMap) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void batchDelete(Map<String, UserInfo> userMap) {

    }
}
