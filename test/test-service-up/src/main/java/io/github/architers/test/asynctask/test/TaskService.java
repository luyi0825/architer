package io.github.architers.test.asynctask.test;

import io.github.architers.test.asynctask.annotation.AsyncTask;

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

    String sendTask();
}
