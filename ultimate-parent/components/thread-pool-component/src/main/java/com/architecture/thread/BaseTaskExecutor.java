package com.architecture.thread;

import com.architecture.thread.RejectedHandler;
import com.architecture.thread.properties.TaskExecutorProperties;
import com.architecture.thread.properties.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luyi
 * 线程池基类
 */
public abstract class BaseTaskExecutor extends ThreadPoolTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BaseTaskExecutor.class);

    /**
     * 得到线程池配置的标识
     *
     * @return 线程池标识ID
     */
    public abstract String getConfigId();

    protected ThreadPoolConfig threadPoolConfig;
    @Autowired
    protected TaskExecutorProperties taskExecutorProperties;

    public BaseTaskExecutor() {
        this.initThreadPoolConfig();
    }

    private void initThreadPoolConfig() {
        threadPoolConfig = getDefaultThreadPoolConfig();
        String configId = this.getConfigId();
        if (!StringUtils.hasText(configId)) {
            throw new IllegalArgumentException("configId不能为空");
        }
        if (taskExecutorProperties != null) {
            Map<String, ThreadPoolConfig> threadPoolConfigMap = taskExecutorProperties.getConfigs();
            if (!CollectionUtils.isEmpty(threadPoolConfigMap) && threadPoolConfigMap.get(configId) != null) {
                threadPoolConfig = threadPoolConfigMap.get(configId);
                logger.info("线程池【{}】采用属性文件的配置:{}", configId, threadPoolConfig);
            } else if (threadPoolConfig != null) {
                logger.info("线程池【{}】采用默认的配置:{}", configId, threadPoolConfig);
            }
        }
        if (threadPoolConfig == null) {
            throw new IllegalArgumentException("未配置线程池【{}】");
        }


    }

    @Override
    public void initialize() {
        //@TODO 校验自义定配置的是否正确
        //核心线程
        this.setCorePoolSize(threadPoolConfig.getCorePoolSize());
        //最大的线程
        this.setMaxPoolSize(threadPoolConfig.getMaxPoolSize());
        //线程池队列大小
        this.setQueueCapacity(threadPoolConfig.getQueueCapacity());
        //非核心线程池存活的时间
        this.setKeepAliveSeconds(threadPoolConfig.getKeepAlive());
        //拒绝策略
        this.setRejectedExecutionHandler(getRejectedExecutionHandler(threadPoolConfig.getRejectedHandler()));
        super.initialize();
    }

    /**
     * 得到拒绝策略
     *
     * @param rejectedHandler 配置的策略
     * @return 拒绝策略:跟线程池中的策略对应
     */
    private RejectedExecutionHandler getRejectedExecutionHandler(RejectedHandler rejectedHandler) {
        switch (rejectedHandler) {
            case callerRunsPolicy:
                return new ThreadPoolExecutor.CallerRunsPolicy();
            case abortPolicy:
                return new ThreadPoolExecutor.AbortPolicy();
            case discardPolicy:
                return new ThreadPoolExecutor.DiscardPolicy();
            case discardOldestPolicy:
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            default:
                throw new IllegalArgumentException("rejectedHandler配置有误！");
        }
    }

    /**
     * 得到默认的线程池配置，当属性配置中没有配置的时候就采用此配置
     */
    protected ThreadPoolConfig getDefaultThreadPoolConfig() {
        return new ThreadPoolConfig();
    }


    public ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }


    public TaskExecutorProperties getTaskExecutorProperties() {
        return taskExecutorProperties;
    }

}
