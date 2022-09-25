package io.github.architers.test.task;

import java.io.Serializable;

/**
 * 任务发送器
 *
 * @author luyi
 */
public interface SendParam extends Serializable {

    /**
     * 获取组名称
     */
    String getGroup();

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

    /**
     * 得到执行器名称
     */
    String getExecutor();

    /**
     * 得到处理器
     */
    String getProcessName();


}
