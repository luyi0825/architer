package io.github.architers.redisson.cache.cacheable.value;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.redisson.cache.CacheUser;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public interface CacheableService {
    @Cacheable(cacheName = "cacheableService_oneCacheable", key = "#userName",cacheOperate =
            CacheableService.class )
    CacheUser oneCacheable(String userName);

    @Cacheable(cacheName = "cacheableService_twoCacheable_key1", key = "#userName")
    @Cacheable(cacheName = "cacheableService_twoCacheable_key2", key = "#result.phone")
    CacheUser twoCacheable(String userName);

    /**
     * 不过期
     */
    @Cacheable(cacheName = "expireTime_never", key = "#userName", expireTime = -1L)
    CacheUser expireTime_never(String userName);


    /**
     * 过期:3分钟
     */
    @Cacheable(cacheName = "expireTime_3_minutes", key = "#userName", expireTime = 3)
    CacheUser expireTime_3_minutes(String userName);

    /**
     * 随机时间
     */
    @Cacheable(cacheName = "randomTime", key = "#userName", expireTime = 120, randomTime = 40)
    CacheUser randomTime(String userName);

    /**
     * userName长度大于10
     */
    @Cacheable(cacheName = "condition", key = "#userName", condition = "#userName.length>10")
    CacheUser condition(String userName);

    /**
     * unless的不缓存
     */
    @Cacheable(cacheName = "unless", key = "#userName", unless = "#userName.startsWith('unless')")
    CacheUser unless(String userName);

    /**
     * 测试并发，没有锁
     */
    @Cacheable(cacheName = "toGather", key = "#userName", expireTime = 10)
    CacheUser toGather(String userName);

    /**
     * 测试并发，加锁
     */
    @Cacheable(cacheName = "testLockToGather", key = "#userName", expireTime = 2)
    CacheUser testLockToGather(String userName);
}
