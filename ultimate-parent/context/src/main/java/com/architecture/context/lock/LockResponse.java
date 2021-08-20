package com.architecture.context.lock;

import java.util.concurrent.locks.Lock;

/**
 * 锁的响应结果
 *
 * @author luyi
 */
public class LockResponse {
    /**
     * 是否获取到锁
     */
    private boolean acquired;
    /**
     * 获取到的锁
     */
    private Lock lock;


    public LockResponse(boolean acquired, Lock lock) {
        this.acquired = acquired;
        this.lock = lock;
    }

    public boolean isAcquired() {
        return acquired;
    }

    public void setAcquired(boolean acquired) {
        this.acquired = acquired;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
