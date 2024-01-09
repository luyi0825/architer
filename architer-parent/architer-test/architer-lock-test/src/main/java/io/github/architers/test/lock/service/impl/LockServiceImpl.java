package io.github.architers.test.lock.service.impl;

import io.github.architers.test.lock.service.LockService;
import org.springframework.stereotype.Service;

@Service
public class LockServiceImpl implements LockService {
    @Override
    public void getLock(String key,long lockTime) {
        System.out.println("call getLock");
        try {
            System.out.println("执行中");
            Thread.sleep(lockTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("执行完毕");

    }

    @Override
    public void getReadLock(String key,long lockTime) {
        System.out.println("call getReadLock");
        try {
            Thread.sleep(lockTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getWriteLock(String key,long lockTime) {
        System.out.println("call getWriteLock");
        try {
            Thread.sleep(lockTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
