package io.github.architers.test.lock.service.impl;

import io.github.architers.test.lock.service.LockService;
import org.springframework.stereotype.Service;

@Service
public class LockServiceImpl implements LockService {
    @Override
    public void getLock(String key) {
        System.out.println("call getLock");
    }

    @Override
    public void getReadLock(String key) {
        System.out.println("call getReadLock");
    }

    @Override
    public void getWriteLock(String key) {
        System.out.println("call getWriteLock");
    }
}
