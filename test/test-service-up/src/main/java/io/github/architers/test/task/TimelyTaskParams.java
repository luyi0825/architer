package io.github.architers.test.task;

import lombok.Data;

import java.util.Arrays;
import java.util.Map;

/**
 * @author luyi
 * 任务请求参数
 */
@Data
public class TimelyTaskParams implements SendParam, Comparable<SendParam> {

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
     * 处理器
     */
    private String processName;

    /**
     * 额外信息
     */
    private Map<String, Object> extraInfo;


    @Override
    public int compareTo(SendParam o) {
        return this.priority - o.getPriority();
    }

    @Override
    public String toString() {
        return "TaskRequestParams{" +
                "taskName='" + taskName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", taskId='" + taskId + '\'' +
                ", priority=" + priority +
                ", reliable=" + reliable +
                ", executor='" + executor + '\'' +
                ", extraInfo=" + extraInfo +
                '}';
    }
}
