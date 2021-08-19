package com.architectrue.lock.zk;

import com.architecture.lock.common.LockService;
import com.architecture.ultimate.module.common.exception.ServiceException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * Zk分布式锁
 */
@Service(LockService.ZK_LOCK_BEAN)
public class ZkLockServiceImpl implements LockService {
    private final Logger logger = LoggerFactory.getLogger(ZkLockServiceImpl.class);
    private CuratorFramework client;
    private final ThreadLocal<InterProcessMutex> interProcessMutexes = new ThreadLocal<>();

    @Override
    public boolean acquire(String lock, long time, TimeUnit timeUnit) {
        boolean acquire;
        try {
            InterProcessMutex mutex = new InterProcessMutex(client, lock);
            acquire = mutex.acquire(time, timeUnit);
            if (acquire) {
                interProcessMutexes.set(mutex);
            }
        } catch (Exception e) {
            logger.error("获取锁失败：{}", lock, e);
            acquire = false;
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
