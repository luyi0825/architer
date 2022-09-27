package io.github.architers.test.task;

import java.util.Set;

/**
 * 异步任务执行器
 *
 * @author luyi
 */
public abstract class AbstractTaskExecutor implements TaskExecutor {


    protected TaskSubscriberTargetScanner taskSubscriberTargetScanner;


    public AbstractTaskExecutor(TaskSubscriberTargetScanner taskSubscriberTargetScanner) {
        this.taskSubscriberTargetScanner = taskSubscriberTargetScanner;
    }

    /**
     * 执行任务
     *
     * @param taskStore
     */
    public void executor(TaskStore taskStore) {
        try {
            AsyncTaskContext.startExecutor();
            Set<TaskSubscriberTarget> tasks = taskSubscriberTargetScanner.taskConsumerTarget(taskStore.getGroup(), taskStore.getTaskName());
            for (TaskSubscriberTarget task : tasks) {
                task.getTaskMethod().invoke(task.getTaskBean(), taskStore.getArgs());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            AsyncTaskContext.endExecutor();
        }


    }
}
