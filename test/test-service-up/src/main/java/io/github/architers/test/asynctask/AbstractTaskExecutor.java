package io.github.architers.test.asynctask;

import java.util.Map;
import java.util.Set;

/**
 * 异步任务执行器
 *
 * @author luyi
 */
public abstract class AbstractTaskExecutor implements TaskExecutor {


    protected TaskRegister taskRegister;



    public AbstractTaskExecutor(TaskRegister taskRegister) {
        this.taskRegister = taskRegister;
    }

    /**
     * 执行任务
     *
     * @param request
     */
    public void executor(TaskSendRequest request) {
        try {
            AsyncTaskContext.startExecutor();
            Set<TaskConsumerTarget> tasks = taskRegister.taskConsumerTarget(request.getTaskName());
            for (TaskConsumerTarget task : tasks) {
                task.getTaskMethod().invoke(task.getTaskBean(), request.getArgs());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            AsyncTaskContext.endExecutor();
        }



    }
}
