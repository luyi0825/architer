package com.architecture.test.cache.lock;

import com.architecture.context.cache.lock.Locked;

import java.util.concurrent.TimeUnit;

public interface CacheLockService {
    @Locked(lockName = "#targetClass.test1().methodName", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.MINUTES)
        //@Cacheable(cacheName = "'test'",key = "#lockKey",expireTime = 2)
    void test1(String lockKey);
}
