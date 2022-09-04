package io.github.architers.cache.lock;


import io.github.architers.contenxt.cache.annotation.Cacheable;
import io.github.architers.contenxt.cache.annotation.PutCache;
import io.github.architers.contenxt.lock.Locked;

import java.util.concurrent.TimeUnit;

public interface CacheLockService {


    //locked = @Locked(lockName = "'CacheLockService_test1'", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
    @Cacheable(cacheName = "test", key = "#lockKey", expireTime = 2)
    String test1(String lockKey);

    @Locked(lockName = "CacheLockService_test1", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.SECONDS)
    @PutCache(cacheName = "test", key = "#lockKey", expireTime = 2)
    void test2(String lockKey);


}
