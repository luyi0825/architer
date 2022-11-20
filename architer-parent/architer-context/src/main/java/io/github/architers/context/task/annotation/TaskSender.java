package io.github.architers.context.task.annotation;

import io.github.architers.context.task.constants.TaskConstants;

import java.lang.annotation.*;

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
     * 任务参数(EL表达式),默认为返回结果
     */
    String taskParam() default "#result";

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 条件满足的时候，不进行缓存操作
     */
    String unless() default "";

    /**
     * 执行器
     */
    String executor() default "";

    /**
     * 数据发送者
     */
    String sender() default "";


}
