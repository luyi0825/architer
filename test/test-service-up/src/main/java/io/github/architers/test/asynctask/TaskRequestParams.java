package io.github.architers.test.asynctask;

import lombok.Data;

import java.util.Map;

/**
 * @author luyi
 * 异步任务请求参数
 */
@Data
public class TaskRequestParams implements TaskSendRequest, Comparable<TaskSendRequest> {

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
     * 额外信息
     */
    private Map<String, Object> extraInfo;


    @Override
    public int compareTo(TaskSendRequest o) {
        return this.priority - o.getPriority();
    }
}
