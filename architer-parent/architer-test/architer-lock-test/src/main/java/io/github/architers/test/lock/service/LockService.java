package io.github.architers.test.lock.service;

import io.github.architers.context.lock.annotation.ExclusiveLock;
import io.github.architers.context.lock.annotation.ReadLock;
import io.github.architers.context.lock.annotation.WriteLock;

public interface LockService {

    @ExclusiveLock(lockName = "exclusiveLock", key = "#key")
    void getLock(String key);

    @ReadLock(lockName = "readWriteLock", key = "#key")
    void getReadLock(String key);

    @WriteLock(lockName = "readWriteLock", key = "#key")
    void getWriteLock(String key);

}
