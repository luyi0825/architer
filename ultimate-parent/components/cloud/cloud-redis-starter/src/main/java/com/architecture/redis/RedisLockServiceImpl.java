package com.architecture.redis;

import com.architecture.context.lock.LockService;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * redis锁实现
 */
public class RedisLockServiceImpl implements LockService {
    private final RedissonClient redissonClient;

    public RedisLockServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getFairLock(lockName);
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return null;
    }


    @Override
    public Lock tryFairLock(String lockName) throws Exception {
        Lock lock = redissonClient.getFairLock(lockName);
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName) throws Exception {
        Lock lock = redissonClient.getLock(lockName);
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getLock(lockName);
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock getWriteLock(String lockName) throws Exception {
        Lock lock = redissonClient.getReadWriteLock(lockName).writeLock();
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock getWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getReadWriteLock(lockName).writeLock();
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock getReadLock(String lockName) throws Exception {
        Lock lock = redissonClient.getReadWriteLock(lockName).readLock();
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock getReadLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getReadWriteLock(lockName).readLock();
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }
}
