package io.github.architers.context.lock.support.jdk;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.LockFactory;
import io.github.architers.context.lock.eums.LockType;

public class JdkLockFactory implements LockFactory {


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
        return LockType.JDK.getType();
    }
}
