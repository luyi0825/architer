package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.DeleteCache;
import io.github.architers.context.cache.annotation.PutCache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface UserInfoService {
    /**
     * 放置本地缓存永远不过期
     */
    void putCacheNeverExpire(UserInfo userInfo);

    /**
     * 测试本地缓存过期五秒
     */
    void putCacheExpireWithFiveSecond(UserInfo userInfo);

    /**
     * 仅从缓存中获取
     */
    UserInfo getOnlyInCache(String username);

    UserInfo getNeverExpire(String username);

    UserInfo getExpireWithFiveSecond(String username);

    /**
     * 删除缓存
     */
    void delete(String username);

    /**
     * 集合批量put不过期
     */
    void collectionBatchPutNeverExpire(List<UserInfo> userInfos);
    /**
     * map批量put不过期
     */
    void mapBatchPutNeverExpire(Map<String, UserInfo> userMap);
}
