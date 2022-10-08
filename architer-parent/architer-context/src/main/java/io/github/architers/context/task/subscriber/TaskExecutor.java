package io.github.architers.context.task.subscriber;


import io.github.architers.context.task.TaskStore;

/**
 * @author luyi
 * 任务执行器
 */
public interface TaskExecutor {

    /**
     * 提交任务
     *
     * @param taskStore 执行的参数
     */
    void executor(TaskStore taskStore);
}
