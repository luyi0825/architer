package io.github.architers.test.task;

import java.util.Set;

/**
 * 异步任务执行器
 *
 * @author luyi
 */
public abstract class AbstractTaskExecutor implements TaskExecutor {


    protected TaskConsumerTargetRegister taskConsumerTargetRegister;



    public AbstractTaskExecutor(TaskConsumerTargetRegister taskConsumerTargetRegister) {
        this.taskConsumerTargetRegister = taskConsumerTargetRegister;
    }

    /**
     * 执行任务
     *
     * @param param
     */
    public void executor(SendParam param) {
        try {
            AsyncTaskContext.startExecutor();
            Set<TaskConsumerTarget> tasks = taskConsumerTargetRegister.taskConsumerTarget(param.getGroup(),param.getTaskName());
            for (TaskConsumerTarget task : tasks) {
                task.getTaskMethod().invoke(task.getTaskBean(), param.getArgs());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            AsyncTaskContext.endExecutor();
        }



    }
}
