package io.github.architers.test.asynctask;

/**
 * 异步定时任务
 *
 * @author luyi
 */
public @interface AsyncTimeTask {

    /**
     * 组
     */
    String group() default "default";

    /**
     * 任务名称
     */
    String taskName();

    /**
     * 优先级
     */
    int priority() default 5;
}
