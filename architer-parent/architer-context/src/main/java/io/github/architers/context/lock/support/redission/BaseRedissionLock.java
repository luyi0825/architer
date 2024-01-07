package io.github.architers.context.lock.support.redission;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.ArchiterReadWriteLock;
import io.github.architers.context.lock.eums.LockMode;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class BaseRedissionLock implements ArchiterReadWriteLock {

    private RLock rLock;

    public BaseRedissionLock(RLock rLock) {
        this.rLock = rLock;
    }

    @Override
    public String getLockType() {
        return null;
    }

    @Override
    public LockMode getLockMode() {
        return null;
    }

    @Override
    public boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(timeout,leaseTime,unit);
    }

    @Override
    public void lock() {
        rLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        rLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(time,unit);
    }

    @Override
    public void unlock() {
        rLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return rLock.newCondition();
    }

    @Override
    public ArchiterLock readLock() {
        return null;
    }

    @Override
    public ArchiterLock writeLock() {
        return null;
    }
}
