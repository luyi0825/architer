package io.github.architers.context.lock.support.jdk;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.eums.LockType;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class BaseJdkLock implements ArchiterLock {


    abstract Lock getLock();

    @Override
    public boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException {
        //TODO
        return getLock().tryLock(timeout, unit);
    }

    @Override
    public void lock() {
        getLock().lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        getLock().lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return getLock().tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return getLock().tryLock(time, unit);
    }

    @Override
    public void unlock() {
        getLock().unlock();
    }

    @Override
    public Condition newCondition() {
        return getLock().newCondition();
    }
}
