package io.github.architers.context.task.annotation;

import io.github.architers.context.task.constants.TaskConstants;

import java.util.concurrent.TimeUnit;

/**
 * 延迟任务发送
 *
 * @author luyi
 */
public @interface DelayedTaskSender {

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
     * 任务参数(EL表达式),默认返回结果
     */
    String taskParam() default "#result";

    /**
     * 延迟时间
     */
    long delayedTime() default -1;

    /**
     * 时间单位(默认单位秒)
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;


}
