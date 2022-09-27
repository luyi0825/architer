package io.github.architers.test.task;

import java.io.Serializable;

/**
 * 发送的参数信息
 *
 * @author luyi
 */
public interface TaskParam extends Serializable {

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

    /**
     * 发送的拓展参数
     */
    SenderExtend getSenderExtend();


    /**
     * 转换为任务存储的数据
     *
     * @return 任务存储
     */
    default TaskStore convert2TaskStore() {
        TaskStore taskStore = new TaskStore();
        taskStore.setTaskId(this.getTaskId());
        taskStore.setTaskName(this.getTaskName());
        taskStore.setArgs(this.getArgs());
        taskStore.setExecutor(this.getExecutor());
        taskStore.setGroup(this.getGroup());
    }


}
