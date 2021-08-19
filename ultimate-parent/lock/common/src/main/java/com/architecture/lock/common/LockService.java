package com.architecture.lock.common;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 锁的接口类
 */
public interface LockService {

    String REDIS_LOCK_BEAN = "com.architecture.lock.redis";
    String JDK_LOCK_BEAN = "com.architecture.lock.redis";
    String ZK_LOCK_BEAN = "com.architecture.lock.zk";

    /**
     * 获取锁
     *
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 是否获取成功
     */
    boolean acquire(String lock, long time, TimeUnit timeUnit);

    /**
     * 释放锁
     */
    void release();
}
