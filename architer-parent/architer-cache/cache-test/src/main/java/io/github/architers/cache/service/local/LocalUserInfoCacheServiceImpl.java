package io.github.architers.cache.service.local;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.UserInfoService;
import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.DeleteCache;
import io.github.architers.context.cache.annotation.PutCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("localUserInfoCache")
public class LocalUserInfoCacheServiceImpl implements ILocalUserInfoCache {
    /**
     * 放置本地缓存永远不过期
     */
    @Override
    @PutCache(cacheName = "local", key = "#userInfo.username", cacheValue = "#userInfo")
    public void putCacheNeverExpire(UserInfo userInfo) {

    }

    /**
     * 测试本地缓存过期五秒
     */
    @Override
    @PutCache(cacheName = "local", key = "#userInfo.username", cacheValue = "#userInfo", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    public void putCacheExpireWithFiveSecond(UserInfo userInfo) {

    }

    /**
     * 仅从缓存中获取
     */
    @Override
    @Cacheable(cacheName = "local", key = "#username")
    public UserInfo getOnlyInCache(String username) {
        return null;
    }
    @Override
    @Cacheable(cacheName = "local", key = "#username")
    public UserInfo getNeverExpire(String username) {
        System.out.println("getNeverExpire查询");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }
    @Override
    @Cacheable(cacheName = "local", key = "#username", expireTime = 5, timeUnit = TimeUnit.SECONDS)
    public UserInfo getExpireWithFiveSecond(String username) {
        System.out.println("getExpireWithFiveSecond查询");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }

    /**
     * 删除缓存
     */
    @Override
    @DeleteCache(cacheName = "local", key = "#username")
    public void delete(String username) {

    }

    /**
     * 集合批量put不过期
     */
    @Override
    @BatchPutCache(cacheName = "local", cacheValue = "#userInfos")
    public void collectionBatchPutNeverExpire(List<UserInfo> userInfos) {

    }

    /**
     * map批量put不过期
     */
    @Override
    @BatchPutCache(cacheName = "local", cacheValue = "#userMap")
    public void mapBatchPutNeverExpire(Map<String, UserInfo> userMap) {

    }
}
