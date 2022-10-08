package io.github.architers.context.task.send;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 延迟任务参数
 *
 * @author luyi
 */
@Data
public class DelayedTaskParam implements TaskParam {

    /**
     * 组名称
     */
    private String group;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务参数
     */
    private Object[] args;

    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 延迟时间
     */
    long delayedTime;

    /**
     * 时间单位
     */
    TimeUnit timeUnit;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 是否可靠消息
     */
    private boolean reliable;

    /**
     * 执行器名称
     */
    private String executor;

    /**
     * 处理器名称
     */
    private String sender;



}
