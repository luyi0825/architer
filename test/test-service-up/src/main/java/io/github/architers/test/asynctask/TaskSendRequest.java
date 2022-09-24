package io.github.architers.test.asynctask;

import java.io.Serializable;

/**
 * 任务发送器
 *
 * @author luyi
 */
public interface TaskSendRequest extends Serializable {

    /**
     * 任务名称
     */
    String getTaskName();

    /**
     * 任务ID
     */
    String getTaskId();

    /**
     * 优先级
     */
    int getPriority();

    /**
     * 可靠性
     */
     boolean isReliable();

    /**
     * 得到任务参数
     */
    Object[] getArgs();

}
