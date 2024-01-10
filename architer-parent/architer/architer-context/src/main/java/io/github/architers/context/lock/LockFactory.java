package io.github.architers.context.lock;

public interface LockFactory {

    /**
     * 获取排他锁
     *
     * @param lockName 锁的名称
     * @param lockKey  锁的key
     */
    ArchiterLock getExclusiveLock(String lockName, String lockKey);

    ArchiterLock getReadLock(String lockName, String lockKey);

    ArchiterLock getWriteLock(String lockName, String lockKey);

    /**
     * 获取锁的类型
     */
    String getLockType();
}
