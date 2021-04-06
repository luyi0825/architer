package com.lz.thread;

import com.lz.thread.properties.TaskExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 公共的线程池：
 * 1.为了解决系统线程池过多的问题
 * 2.可以根据配置修改线程池，不必改代码，增加灵活度
 * 3.可以处理一些异步,用户不需要立即收到任务处理结果的任务，比如系统的定时任务,系统预警发送邮箱,日志等
 * 注意：
 * 1.例如一些查询优化（采用多线程加快查询速度），需要及时响应需要用到线程池的地方，请自定义线程池（防止线程池的队列太长，影响核心业务、用户体验等）
 * 2.如果该服务CommonTaskExecutor其他地方使用的比较少可以用这个
 *
 * @author luyi
 */
public class CommonTaskExecutor extends ThreadPoolTaskExecutor {

    private TaskExecutorProperties taskExecutorProperties;

    public CommonTaskExecutor(TaskExecutorProperties taskExecutorProperties) {
        this.taskExecutorProperties = taskExecutorProperties;
    }

    @Override
    public void initialize() {
        //核心线程
        this.setCorePoolSize(taskExecutorProperties.getCorePoolSize());
        //最大的线程
        this.setMaxPoolSize(taskExecutorProperties.getMaxPoolSize());
        //线程池队列大小
        this.setQueueCapacity(taskExecutorProperties.getQueueCapacity());
        //非核心线程池存活的时间
        this.setKeepAliveSeconds(taskExecutorProperties.getKeepAlive());
        //拒绝策略
        this.setRejectedExecutionHandler(getRejectedExecutionHandler(taskExecutorProperties.getRejectedHandler()));
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
}
