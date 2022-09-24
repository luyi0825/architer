package io.github.architers.test.asynctask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfiguration {


     @Bean
    public AsyncTaskAspect asyncTaskAspect(TaskSubmit taskSubmit) {
        return new AsyncTaskAspect(taskSubmit);
    }


    @Bean
    public TaskRegister asyncTaskRegister() {
        return new TaskRegister();
    }


    @Bean
    public ThreadPoolExecutor threadPoolTaskExecutor() {

        //  RBlockingDeque<Runnable> blockingDeque = redissonClient.getBlockingDeque("test");
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1000);
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        ThreadGroup threadGroup = new ThreadGroup("test-group");
        threadFactory.setThreadGroup(threadGroup);
        threadFactory.setThreadNamePrefix("test");
        return new ThreadPoolExecutor(20,
                20,
                60,
                TimeUnit.MILLISECONDS,
                blockingQueue,
                threadFactory
        );

    }

}
