package io.github.architers.test.task.annotation;

import io.github.architers.test.task.constants.ConsumerSources;
import io.github.architers.test.task.constants.TaskConstants;

import java.lang.annotation.*;

/**
 * 任务订阅者
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TaskSubscriber {

    /**
     * 组
     */
    String group() default TaskConstants.DEFAULT_GROUP;

    /**
     * 任务名称
     */
    String taskName();

    /**
     * 数据消费来源(如果任务被发送的不同的中间件（例如kafka,redis,rocketMq），就需要指定)
     */
    ConsumerSources[] sources();

    /**
     * 是否可靠消息(如果false，说明改消息可以丢失)
     */
    boolean reliable() default true;

    /**
     * 优先级
     */
    int priority() default 2;


}
