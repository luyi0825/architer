package io.github.architers.context.lock;//package io.github.architers.context.cache.lock;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * @author luyi
 * jdk的锁
 */
public class JdkLockServiceImpl implements LockService {


    private Map<String, Lock> lockMap = new ConcurrentHashMap<>();


    @Override
    public String getLockSplit() {
        return ":";
    }

    @Override
    public Lock tryFairLock(String lockName) throws Exception {
        Lock lock = lockMap.computeIfAbsent(lockName, (Function) o -> new ReentrantLock(true));
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = lockMap.computeIfAbsent(lockName, (Function) o -> new ReentrantLock(true));
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName) throws Exception {
        Lock lock = lockMap.computeIfAbsent(lockName, (Function) o -> new ReentrantLock(false));
        if (lock.tryLock()) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = lockMap.computeIfAbsent(lockName, (Function) o -> new ReentrantLock(false));
        if (lock.tryLock(time,timeUnit)) {
            return lock;
        }
        return null;
    }

    @Override
    public Lock tryWriteLock(String lockName) throws Exception {
        return null;
    }

    @Override
    public Lock tryWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }

    @Override
    public Lock tryReadLock(String lockName) throws Exception {
        return null;
    }

    @Override
    public Lock tryReadLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        return null;
    }
}
