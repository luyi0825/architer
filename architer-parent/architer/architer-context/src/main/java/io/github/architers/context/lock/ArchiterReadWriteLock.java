package io.github.architers.context.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 读写锁
 *
 * @author luyi
 * @since 1.0.3
 */
public interface ArchiterReadWriteLock extends ReadWriteLock, ArchiterLock {

    @Override
    ArchiterLock readLock();

    @Override
    ArchiterLock writeLock();
}
