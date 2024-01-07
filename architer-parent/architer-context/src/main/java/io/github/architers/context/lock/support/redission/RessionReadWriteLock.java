package io.github.architers.context.lock.support.redission;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.ArchiterReadWriteLock;
import io.github.architers.context.lock.eums.LockMode;
import org.redisson.api.RReadWriteLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RessionReadWriteLock implements ArchiterReadWriteLock {

    private RReadWriteLock rReadWriteLock;

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
        return false;
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
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
