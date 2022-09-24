package io.github.architers.test.asynctask.test;

import io.github.architers.test.asynctask.LocalTransactionExecutor;
import io.github.architers.test.asynctask.annotation.AsyncTask;
import io.github.architers.test.asynctask.annotation.TaskConsumer;
import io.github.architers.test.asynctask.annotation.TaskSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author luyi
 */
@Service
public class TaskServiceImpl implements TaskService {
    /**
     * 添加异步任务
     *
     * @param username
     * @param test
     */
    @AsyncTask(taskName = "testTask")
    public void addAsyncTask(String username, String test) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(username + "|" + test);
    }

    @Override
    @TaskSender(taskName = "testTask2",executor = "localTransactionExecutor")
    public String sendTask() {
        return UUID.randomUUID().toString();
    }

    @TaskConsumer(taskName = "testTask2")
    public void consumer1(String str) {
        System.out.println("consumer1:" + str);
    }

    @TaskConsumer(taskName = "testTask2")
    public void consumer2(String str) {
        System.out.println("consumer2:" + str);
    }
}
