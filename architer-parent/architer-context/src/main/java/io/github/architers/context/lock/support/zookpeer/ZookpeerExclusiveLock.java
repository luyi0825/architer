package io.github.architers.context.lock.support.zookpeer;

import io.github.architers.context.lock.ArchiterExclusiveLock;
import org.apache.curator.framework.recipes.locks.InterProcessLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * zk分布式排他锁
 *
 * @author luyi
 */
public class ZookpeerExclusiveLock implements ArchiterExclusiveLock {
    private InterProcessLock interProcessLock;

    public ZookpeerExclusiveLock() {

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
