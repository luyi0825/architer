package io.github.architers.context.thread.client.parse;


import io.github.architers.context.thread.client.annotation.ThreadPoolClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luyi
 */
public class ThreadPoolParser implements ApplicationContextAware {


    List<ThreadPoolTaskExecutor> threadPoolTaskExecutors=new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //得到所有需要监控的线程池
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(ThreadPoolClient.class);
        map.forEach((key, value) -> {
            if (!(value instanceof ThreadPoolTaskExecutor)) {
                throw new RuntimeException(value.getClass().getName() + " is invalid for ThreadPoolClient");
            }
            threadPoolTaskExecutors.add((ThreadPoolTaskExecutor) value);
        });
    }


}
