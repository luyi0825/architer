package io.github.architers.context.lock.support.zookpeer;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.LockFactory;

public class ZookpeerLockFactory implements LockFactory {
    @Override
    public ArchiterLock getExclusiveLock(String lockName, String lockKey) {
        return null;
    }

    @Override
    public ArchiterLock getReadLock(String lockName, String lockKey) {
        return null;
    }

    @Override
    public ArchiterLock getWriteLock(String lockName, String lockKey) {
        return null;
    }

    @Override
    public String getLockType() {
        return null;
    }
}
