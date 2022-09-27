package io.github.architers.test.task;

import io.github.architers.test.task.constants.ConsumerSources;
import org.springframework.context.ApplicationContext;

import java.util.Set;
import java.util.concurrent.Flow;

/**
 * @author 任务订阅者注册器
 */
public interface TaskSubscriberRegister {

    /**
     * 订阅源
     */
    ConsumerSources getSubscriberSource();

    /**
     * 订阅的数据源
     */
    void register(ApplicationContext applicationContext, Set<String> groups);

}
