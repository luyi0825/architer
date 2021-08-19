package com.architecture.es.sync;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


import java.util.concurrent.ThreadPoolExecutor;


@Component
public class TaskExecutor extends ThreadPoolTaskExecutor {
    @Override
    public void initialize() {
        //@TODO 校验自义定配置的是否正确
        //核心线程
        this.setCorePoolSize(20);
        //最大的线程
        this.setMaxPoolSize(100);
        //线程池队列大小
        this.setQueueCapacity(500);
        //拒绝策略
        this.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        super.initialize();
    }
}
