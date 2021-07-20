package com.architecture.ultimate.thread.properties;


import com.architecture.ultimate.thread.RejectedHandler;

/**
 * 线程池配置
 *
 * @author luyi
 */
public class ThreadPoolConfig {
    /**
     * 核心线程数量
     */
    private int corePoolSize = 10;

    /**
     * 总的线程数量
     */
    private int maxPoolSize = 100;

    /**
     * 线程池队列容量
     */
    private int queueCapacity = 500;

    /**
     * 非核心线程池的存活时间:秒
     */
    private int keepAlive = 30;

    /**
     * 拒绝策略
     * 默认abortPolicy
     *
     * @see RejectedHandler
     */
    private RejectedHandler rejectedHandler = RejectedHandler.abortPolicy;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public RejectedHandler getRejectedHandler() {
        return rejectedHandler;
    }

    public void setRejectedHandler(RejectedHandler rejectedHandler) {
        this.rejectedHandler = rejectedHandler;
    }
}
