package io.github.architers.context.task.annotation;

import io.github.architers.context.task.constants.TaskConstants;
import io.github.architers.context.task.constants.TaskPriority;

/**
 * 优先级任务发送
 *
 * @author luyi
 */
public @interface PriorityTaskSender {
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
    TaskPriority priority() default TaskPriority.four;

    /**
     * 任务参数(EL表达式)
     */
    String taskParam() default "#result";

    /**
     * 执行器
     */
    String executor() default "";

    /**
     * 数据发送者
     */
    String sender() default "";


}
