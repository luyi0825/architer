package io.github.architers.cache.lock;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CachePut;
import io.github.architers.context.lock.annotation.ExclusiveLock;

import java.util.concurrent.TimeUnit;

public interface CacheLockService {


    //locked = @Locked(lockName = "'CacheLockService_test1'", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
    @Cacheable(cacheName = "test", key = "#lockKey", expireTime = 2)
    String test1(String lockKey);

    @ExclusiveLock(lockName = "CacheLockService_test1", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
    @CachePut(cacheName = "test", key = "#lockKey", expireTime = 2,cacheValue = "#lockKey")
    void test2(String lockKey);


}
