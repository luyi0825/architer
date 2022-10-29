package io.github.architers.redisson.cache.cacheable.map;


import io.github.architers.redisson.cache.CacheUser;
import io.github.architers.redisson.cache.support.MapCacheOperate;
import io.github.architers.context.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public interface MapCacheableService {
    @Cacheable(cacheName = "mapCacheableService:oneCacheable", key = "#userName", cacheOperate =
            MapCacheOperate.class)
    CacheUser oneCacheable(String userName);

    @Cacheable(cacheName = "mapCacheableService:twoCacheable_key1", key = "#userName",
            cacheOperate =
                    MapCacheOperate.class)
    @Cacheable(cacheName = "mapCacheableService:twoCacheable_key2", key = "#result.phone",
            cacheOperate =
                    MapCacheOperate.class)
    CacheUser twoCacheable(String userName);

    /**
     * 不过期
     */
    @Cacheable(cacheName = "mapCacheableService:expireTime_never", key = "#userName", expireTime
            = -1L, cacheOperate =
            MapCacheOperate.class)
    CacheUser expireTime_never(String userName);


    /**
     * 过期:1分钟
     */
    @Cacheable(cacheName = "mapCacheableService:expireTime_1_minutes", key = "#userName",
            cacheOperate =
                    MapCacheOperate.class, expireTime = 1)
    CacheUser expireTime_1_minutes(String userName);

    /**
     * 随机时间
     */
    @Cacheable(cacheName = "mapCacheableService:randomTime", key = "#userName", expireTime = 120,
            randomTime = 40, cacheOperate = MapCacheOperate.class)
    CacheUser randomTime(String userName);

    /**
     * userName长度大于10
     */
    @Cacheable(cacheName = "mapCacheableService:condition", key = "#userName", condition =
            "#userName.length>10", cacheOperate = MapCacheOperate.class)
    CacheUser condition(String userName);

    /**
     * unless的不缓存
     */
    @Cacheable(cacheName = "mapCacheableService:unless", key = "#userName", unless = "#userName" +
            ".startsWith('unless')", cacheOperate = MapCacheOperate.class)
    CacheUser unless(String userName);

    /**
     * 测试并发，没有锁
     */
    @Cacheable(cacheName = "mapCacheableService:toGather", key = "#userName", expireTime = 10)
    CacheUser toGather(String userName);

    /**
     * 测试并发，加锁
     */
    @Cacheable(cacheName = "mapCacheableService:testLockToGather", key = "#userName", expireTime = 2)
    CacheUser testLockToGather(String userName);
}
