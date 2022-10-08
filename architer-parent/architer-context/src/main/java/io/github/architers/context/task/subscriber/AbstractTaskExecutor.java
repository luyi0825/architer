package io.github.architers.context.task.subscriber;

import io.github.architers.context.task.TaskStore;

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
     * @param taskStore 任务信息
     */
    public void executor(TaskStore taskStore) {
        try {
            TaskExecutorContext.startExecutor();
            Set<TaskSubscriberTarget> tasks = taskSubscriberTargetScanner.taskConsumerTarget(taskStore.getGroup(), taskStore.getTaskName());
            for (TaskSubscriberTarget task : tasks) {
                task.getTaskMethod().invoke(task.getTaskBean(), taskStore.getArgs());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            TaskExecutorContext.endExecutor();
        }
    }
}
