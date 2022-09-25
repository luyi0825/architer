package io.github.architers.test.task.annotation;

import io.github.architers.test.task.constants.TaskConstants;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 任务发送器
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TaskSender {

    /**
     * 组
     */
    String group() default TaskConstants.DEFAULT_GROUP;

    /**
     * 任务名称
     */
    String taskName();

    /**
     * 是否可靠消息(如果false，说明改消息可以丢失)
     */
    boolean reliable() default true;

    /**
     * 优先级
     */
    int priority() default 2;

    /**
     * 延迟时间
     */
    long delayedTime() default -1;

    /**
     * 时间单位(默认单位秒)
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 执行器
     */
    String executor() default "";

    /**
     * 处理器
     */
    String process() default "";


}
