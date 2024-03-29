package io.github.architers.context.lock.support.jdk;

import io.github.architers.context.lock.eums.LockMode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JdkReadLock extends BaseJdkLock {

    private final ReentrantReadWriteLock lock;


    public JdkReadLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }

    @Override
    Lock getLock() {
        return lock.readLock();
    }



    @Override
    public boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException {
        return super.tryLock(timeout, leaseTime, unit);
    }

    @Override
    public void lock() {
        super.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        super.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return super.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return super.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        super.unlock();
    }

    @Override
    public Condition newCondition() {
        return newCondition();
    }
}
