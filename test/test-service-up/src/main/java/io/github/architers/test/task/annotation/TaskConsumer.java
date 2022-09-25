package io.github.architers.test.task.annotation;

import io.github.architers.test.task.constants.TaskConstants;

import java.lang.annotation.*;

/**
 * 任务消费
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TaskConsumer {

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


}
