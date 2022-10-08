package io.github.architers.context.task.subscriber;

import io.github.architers.context.task.constants.ConsumerSources;
import org.springframework.context.ApplicationContext;

import java.util.Set;

/**
 * @author 任务订阅者注册器
 */
public interface TaskSubscriberRegister {

    /**
     * 订阅者的源
     *
     * @return 消息订阅的源
     */
    ConsumerSources getSubscriberSource();

    /**
     * 注册定于订阅者
     *
     * @param groups             组信息
     * @param applicationContext 应用上下文
     */
    void register(ApplicationContext applicationContext, Set<String> groups);

}
