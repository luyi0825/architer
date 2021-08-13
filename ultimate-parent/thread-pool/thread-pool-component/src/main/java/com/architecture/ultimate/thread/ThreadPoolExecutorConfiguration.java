package com.architecture.ultimate.thread;

import com.architecture.ultimate.thread.properties.TaskExecutorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置类
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(TaskExecutorProperties.class)
public class ThreadPoolExecutorConfiguration {

    public ThreadPoolExecutorConfiguration() {
        System.out.println("ThreadPoolExecutorConfiguration init");
    }

}
