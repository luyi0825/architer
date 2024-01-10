package io.github.architers.lock.redisson;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.LockFactory;
import io.github.architers.context.lock.eums.LockType;
import org.redisson.api.RedissonClient;

/**
 * redisson锁的工厂类
 *
 * @author luyi
 * @since 1.0.3
 */
public class RedissonLockFactory implements LockFactory {

    private final RedissonClient redissonClient;

    public RedissonLockFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public ArchiterLock getExclusiveLock(String lockName, String lockKey) {
        return new BaseRedissonLock(redissonClient.getMap(lockName).getLock(lockKey));
    }


    @Override
    public ArchiterLock getReadLock(String lockName, String lockKey) {
        return new BaseRedissonLock(redissonClient.getMap(lockName).getReadWriteLock(lockKey).readLock());
    }

    @Override
    public ArchiterLock getWriteLock(String lockName, String lockKey) {
        return new BaseRedissonLock(redissonClient.getMap(lockName).getReadWriteLock(lockKey).writeLock());
    }


    @Override
    public String getLockType() {
        return LockType.REDISSON.getType();
    }
}
