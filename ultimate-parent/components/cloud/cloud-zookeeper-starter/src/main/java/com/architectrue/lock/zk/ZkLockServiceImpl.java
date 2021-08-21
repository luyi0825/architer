package com.architectrue.lock.zk;


import com.architecture.context.lock.LockService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * Zk分布式锁
 */
@Service(LockService.ZK_LOCK_BEAN)
public class ZkLockServiceImpl implements LockService {
    private CuratorFramework client;
    private static final String ZK_LOCK_START = "/";

    @Override
    public Lock tryLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Assert.hasText(lockName, "lock is null");
        if (!lockName.startsWith(ZK_LOCK_START)) {
            lockName = ZK_LOCK_START + lockName;
        }
        InterProcessMutex mutex = new InterProcessMutex(client, lockName);
        if (mutex.acquire(time, timeUnit)) {
            return new ZookeeperLock(mutex);
        }
        return null;
    }

    @Override
    public Lock tryLock(String lockName) throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(client, lockName);
        mutex.acquire();
        return new ZookeeperLock(mutex);
    }

    @Override
    public void unLock(Lock lock) {
        if (lock != null) {
            lock.unlock();
        }

    }

    @Autowired
    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
