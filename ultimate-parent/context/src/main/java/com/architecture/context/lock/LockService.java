package com.architecture.context.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 锁的接口类
 */
public interface LockService {
    /**
     * LockService对应实现类的Bean名称
     */
    String REDIS_LOCK_BEAN = "com.architecture.lock.redis";
    String JDK_LOCK_BEAN = "com.architecture.lock.redis";
    String ZK_LOCK_BEAN = "com.architecture.lock.zk";

    /**
     * 获取锁
     * <li>获取的是公平的/li>
     * <li>请手动释放锁/li>
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryLock(String lockName, long time, TimeUnit timeUnit) throws Exception;

    /**
     * 获取锁，直到获取到
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     */
    Lock tryLock(String lockName) throws Exception;

    /**
     * 释放锁
     *
     * @param lock 对应tryLock的返回值
     */
    void unLock(Lock lock);

}
