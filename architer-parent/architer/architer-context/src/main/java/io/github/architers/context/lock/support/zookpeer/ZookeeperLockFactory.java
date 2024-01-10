package io.github.architers.context.lock.support.zookpeer;

import io.github.architers.context.lock.ArchiterLock;
import io.github.architers.context.lock.LockFactory;
import io.github.architers.context.lock.eums.LockType;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * zk分布式工厂类
 *
 * @author luyi
 * @since 1.0.3
 */
public class ZookeeperLockFactory implements LockFactory {


    private CuratorFramework client;
    private static final String ZK_LOCK_START = "/";

    @Override
    public ArchiterLock getExclusiveLock(String lockName, String lockKey) {
        InterProcessLock interProcessLock = new InterProcessMutex(client, buildPath(lockName, lockKey));
        return new ZookeeperLock(interProcessLock);
    }

    private String buildPath(String lockName, String lockKey) {
        return String.join(ZK_LOCK_START, lockName, lockKey);
    }

    @Override
    public ArchiterLock getReadLock(String lockName, String lockKey) {
        String path = buildPath(lockName, lockKey);
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, path);
        return new ZookeeperLock(interProcessReadWriteLock.readLock());
    }

    @Override
    public ArchiterLock getWriteLock(String lockName, String lockKey) {
        String path = buildPath(lockName, lockKey);
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, path);
        return new ZookeeperLock(interProcessReadWriteLock.writeLock());
    }

    @Override
    public String getLockType() {
        return LockType.ZK.getType();
    }

    @Autowired
    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
