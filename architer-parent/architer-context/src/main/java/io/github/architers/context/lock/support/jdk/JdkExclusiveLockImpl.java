package io.github.architers.context.lock.support.jdk;

import io.github.architers.context.lock.eums.LockMode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * jdk排他锁
 *
 * @author luyi
 * @since 1.0.3
 */
public class JdkExclusiveLockImpl extends BaseJdkLock {

    private final ReentrantLock reentrantLock = new ReentrantLock();

    public JdkExclusiveLockImpl() {

    }

    @Override
    Lock getLock() {
        return reentrantLock;
    }

    @Override
    public LockMode getLockMode() {
        return LockMode.exclusive;
    }
}
