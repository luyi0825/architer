package io.github.architers.test.task.test;

import io.github.architers.test.task.annotation.AsyncTask;
import io.github.architers.test.task.annotation.TaskSender;

/**
 * @author luyi
 */
public interface TaskService {
    /**
     * 添加异步任务
     *
     * @param username
     * @param test
     */
    @AsyncTask(taskName = "testTask")
    void addAsyncTask(String username, String test) throws InterruptedException;
    @TaskSender(taskName = "testTask2",executor = "localTransactionExecutor")
    String sendTask();
}
