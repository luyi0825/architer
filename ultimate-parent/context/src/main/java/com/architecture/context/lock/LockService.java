package com.architecture.context.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 锁的接口类
 */
public interface LockService {
    /**
     * LockService对应实现类的Bean名称
     */
    String REDIS_LOCK_BEAN = "com.architecture.module.common.lock.redis";
    String JDK_LOCK_BEAN = "com.architecture.module.common.lock.redis";
    String ZK_LOCK_BEAN = "com.architecture.module.common.lock.zk";

    /**
     * 获取锁
     *
     * @param lock     锁的名称:zk中.会被替换成/
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 是否获取成功
     * @throws Exception 获取锁失败的异常
     */
    boolean acquire(String lock, long time, TimeUnit timeUnit) throws Exception;

    /**
     * 释放锁
     */
    void release();
}
