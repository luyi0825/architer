package io.github.architers.lock.redisson;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.ArchiterReadWriteLock;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class BaseRedissonLock implements ArchiterReadWriteLock {

    private final RLock rLock;

    public BaseRedissonLock(RLock rLock) {
        this.rLock = rLock;
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
