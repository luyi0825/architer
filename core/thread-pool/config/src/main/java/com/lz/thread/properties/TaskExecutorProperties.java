package com.lz.thread.properties;


import com.lz.thread.RejectedHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义公共线程池配置属性
 *
 * @author luyi
 */
@Data
@ConfigurationProperties(prefix = "customize.thread-pool", ignoreInvalidFields = true)
public class TaskExecutorProperties {
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


}
