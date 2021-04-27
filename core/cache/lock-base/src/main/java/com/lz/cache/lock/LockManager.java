package com.lz.cache.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存对应的锁
 */
public interface LockManager {
    /**
     * 得到读锁
     *
     * @param lock 锁
     * @return 读锁
     */
    Lock getReadLock(String lock);

    /**
     * 得到写锁
     *
     * @param lock 锁
     * @return 写锁
     */
    Lock getWriteLock(String lock);

    /**
     * 得到重入锁
     *
     * @param lock 锁
     * @return 冲入锁
     */
    Lock getReentrantLock(String lock);

}
