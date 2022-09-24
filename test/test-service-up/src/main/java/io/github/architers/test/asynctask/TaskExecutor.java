package io.github.architers.test.asynctask;

/**
 * @author luyi
 * 任务执行器
 */
public interface TaskExecutor {

    /**
     * 提交任务
     */
    void executor(TaskSendRequest request);
}
