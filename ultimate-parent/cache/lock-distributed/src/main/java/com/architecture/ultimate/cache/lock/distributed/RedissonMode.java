package com.architecture.ultimate.cache.lock.distributed;

/**
 * @author luyi
 * redisson的模式
 */
public enum RedissonMode {
    /**
     * 单节点
     */
    single(),
    /**
     * 集群
     */
    cluster(),
    /**
     * 主从
     */
    master_slave()
}
