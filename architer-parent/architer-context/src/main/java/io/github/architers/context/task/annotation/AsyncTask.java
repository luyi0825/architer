package io.github.architers.context.task.annotation;

import io.github.architers.context.task.send.NullSenderExtend;
import io.github.architers.context.task.send.SenderExtend;
import io.github.architers.context.task.constants.TaskConstants;

import java.lang.annotation.*;

/**
 * 异步任务
 * <li>自己发送，自己消费</li>
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AsyncTask {

    /**
     * 组
     */
    String group() default TaskConstants.DEFAULT_GROUP;

    /**
     * 任务名称
     */
    String taskName();

    /**
     * 是否可靠消息(如果false，说明该消息可以丢失)
     */
    boolean reliable() default true;

    /**
     * 拓展参数(可以根据这些参数做定制化)
     */
    Class<? extends SenderExtend> extend() default NullSenderExtend.class;

    /**
     * 执行器
     */
    String executor() default "";

    /**
     * 数据发送者
     */
    String sender() default "";

    /**
     * 优先级
     */
    int priority() default 2;


}
