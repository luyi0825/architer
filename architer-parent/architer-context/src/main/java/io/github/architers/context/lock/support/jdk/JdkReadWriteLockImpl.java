package io.github.architers.context.lock.support.jdk;//package io.github.architers.context.lock.jdk;//package io.github.architers.context.cache.lock;


import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.eums.LockMode;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * jdk的锁读写锁
 *
 * @author luyi
 */
public class JdkReadWriteLockImpl extends BaseJdkLock implements ReadWriteLock, ArchiterLock {

    private final JdkReadLock jdkReadLock;

    public final JdkWriteLock jdkWriteLock;

    public JdkReadWriteLockImpl() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
        jdkReadLock = new JdkReadLock(reentrantReadWriteLock);
        jdkWriteLock = new JdkWriteLock(reentrantReadWriteLock);
    }

    @Override
    Lock getLock() {
        throwCallErrorException();
        return null;
    }

    @Override
    public LockMode getLockMode() {
        return LockMode.readwrite;
    }

    @Override
    public boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException {
        throwCallErrorException();
        return false;
    }

    public void throwCallErrorException() {
        throw new RuntimeException("请调用具体锁的方法");
    }

    @Override
    public void lock() {
        throwCallErrorException();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throwCallErrorException();
    }

    @Override
    public boolean tryLock() {
        throwCallErrorException();
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throwCallErrorException();
        return false;
    }

    @Override
    public void unlock() {
        throwCallErrorException();
    }

    @Override
    public Condition newCondition() {
        throwCallErrorException();
        return null;
    }

    @Override
    @NotNull
    public Lock readLock() {
        return jdkReadLock;
    }

    @Override
    public Lock writeLock() {
        return jdkWriteLock;
    }


}
