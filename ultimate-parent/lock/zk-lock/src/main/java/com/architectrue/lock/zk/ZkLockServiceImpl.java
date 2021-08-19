package com.architectrue.lock.zk;

import com.architecture.lock.common.LockService;
import com.architecture.ultimate.module.common.exception.ServiceException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * Zk分布式锁
 */
@Service(LockService.ZK_LOCK_BEAN)
public class ZkLockServiceImpl implements LockService {
    private CuratorFramework client;
    private final ThreadLocal<InterProcessMutex> interProcessMutexes = new ThreadLocal<>();
    private static final String ZK_LOCK_START = "/";

    @Override
    public boolean acquire(String lock, long time, TimeUnit timeUnit) throws Exception {
        Assert.hasText(lock, "lock is null");
        if (!lock.startsWith(ZK_LOCK_START)) {
            lock = ZK_LOCK_START + lock;
        }
        InterProcessMutex mutex = new InterProcessMutex(client, lock);
        boolean acquire = mutex.acquire(time, timeUnit);
        if (acquire) {
            interProcessMutexes.set(mutex);
        }
        return acquire;
    }


    @Override
    public void release() {
        InterProcessMutex interProcessMutex = interProcessMutexes.get();
        if (interProcessMutex != null) {
            try {
                interProcessMutex.release();
            } catch (Exception e) {
                throw new ServiceException("释放锁失败", e);
            } finally {
                interProcessMutexes.remove();
            }
        }
    }


    @Autowired
    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
