package io.github.architers.context.lock.support;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.eums.LockMode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public final class NoLock implements ArchiterLock {

    private static final NoLock noLock = new NoLock();

    public static ArchiterLock getInstance() {
        return noLock;
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
}
