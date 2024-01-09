package io.github.architers.context.lock.support.zookpeer;

import io.github.architers.context.lock.ArchiterExclusiveLock;
import org.apache.curator.framework.recipes.locks.InterProcessLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * zk分布式锁
 *
 * @author luyi
 * @since
 */
public class ZookeeperLock implements ArchiterExclusiveLock {
    private final InterProcessLock interProcessLock;

    public ZookeeperLock(InterProcessLock interProcessLock) {
        this.interProcessLock = interProcessLock;
    }

    @Override
    public boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException {
        try {
            return interProcessLock.acquire(timeout, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lock() {
        try {
            interProcessLock.acquire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        //TODO
    }

    @Override
    public boolean tryLock() {
        try {
            return interProcessLock.acquire(1000,TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            return interProcessLock.acquire(time, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlock() {
        try {
            if (interProcessLock.isAcquiredInThisProcess()) {
                interProcessLock.release();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
