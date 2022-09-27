package io.github.architers.test.task;

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
