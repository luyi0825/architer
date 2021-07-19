package com.core.thread.client.entity;


import com.core.thread.client.RejectedHandler;
import lombok.Data;

/**
 * @author luyi
 */
@Data
public class ClientInfo {
    /**
     * 核心线程数量
     */
    private int corePoolSize;

    /**
     * 最大的线程数量
     */
    private int maxPoolSize;

    /**
     * 线程池队列容量
     */
    private int queueCapacity;

    /**
     * 非核心线程池的存活时间:秒
     */
    private int keepAlive;

    /**
     * 拒绝策略
     * 默认abortPolicy
     *
     * @see RejectedHandler
     */
    private RejectedHandler rejectedHandler;
}
