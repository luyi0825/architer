package com.lz.lock.distributed;

import java.util.concurrent.locks.Lock;

public class DistributedLock implements LockService{
    @Override
    public Lock getReadLock(String lock) {
        return null;
    }

    @Override
    public Lock getWriteLock(String lock) {
        return null;
    }

    @Override
    public Lock getReentrantLock(String lock) {
        return null;
    }
}
