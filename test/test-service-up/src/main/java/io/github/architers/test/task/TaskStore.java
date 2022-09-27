package io.github.architers.test.task;

import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * 任务数据
 *
 * @author luyi
 */
@Data
public class TaskStore {
    /**
     * 任务组
     */
    private String group;
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务参数
     */
    private Object[] args;

    /**
     * 执行器名称
     */
    private String executor;


}
