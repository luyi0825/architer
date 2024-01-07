package io.github.architers.context.lock.support.redission;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.LockFactory;
import io.github.architers.context.lock.eums.LockType;
import org.redisson.api.RedissonClient;

public class RedissonLockFacotory implements LockFactory {

    private final RedissonClient redissonClient;

    public RedissonLockFacotory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public ArchiterLock getExclusiveLock(String lockName, String lockKey) {
        return new BaseRedissionLock(redissonClient.getMap(lockName).getLock(lockKey));
    }

    @Override
    public ArchiterLock getReadWriteLock(String lockName, String lockKey) {
        return new BaseRedissionLock(redissonClient.getMap(lockName).getReadWriteLock(lockKey).readLock());
    }

    @Override
    public ArchiterLock getReadLock(String lockName, String lockKey) {
        return new BaseRedissionLock(redissonClient.getMap(lockName).getReadWriteLock(lockKey).readLock());
    }

    @Override
    public ArchiterLock getWriteLock(String lockName, String lockKey) {
        return new BaseRedissionLock(redissonClient.getMap(lockName).getReadWriteLock(lockKey).writeLock());
    }


    @Override
    public String getLockType() {
        return LockType.REDISSION.getType();
    }
}
