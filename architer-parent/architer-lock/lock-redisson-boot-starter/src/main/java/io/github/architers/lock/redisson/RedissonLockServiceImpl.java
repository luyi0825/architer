package io.github.architers.lock.redisson;

import io.github.architers.contenxt.Symbol;
import io.github.architers.contenxt.lock.LockService;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * redisson分布式锁实现
 *
 * @author luyi
 */
public class RedissonLockServiceImpl implements LockService {


    private final RedissonClient redissonClient;

    public RedissonLockServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public String getLockSplit() {
        return Symbol.COLON;
    }

    @Override
    public Lock tryFairLock(String lockName) throws Exception {
        return redissonClient.getFairLock(lockName);
    }

    @Override
    public Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName) throws Exception {
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }

    @Override
    public Lock tryWriteLock(String lockName) throws Exception {
        return null;
    }

    @Override
    public Lock tryWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }

    @Override
    public Lock tryReadLock(String lockName) throws Exception {
        return null;
    }

    @Override
    public Lock tryReadLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }
}
