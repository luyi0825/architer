package io.github.architers.test.task.test;

import io.github.architers.context.task.annotation.AsyncTask;
import io.github.architers.context.task.annotation.TaskSender;
import io.github.architers.context.task.constants.SenderName;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author luyi
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private RedissonClient redissonClient;

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
    @TaskSender(taskName = "testTask2", executor = "localTransactionExecutor", sender =
            SenderName.ROCKET_MQ)
    public String sendTask() {
        return UUID.randomUUID().toString();
    }

//    @TaskSubscriber(taskName = "testTask2")
//    public void consumer1(String str) {
//        System.out.println("consumer1:" + str);
//    }
//
//    @TaskSubscriber(taskName = "testTask2")
//    public void consumer2(String str) {
//        System.out.println("consumer2:" + str);
//    }


    /********************************延迟队列*/
    public void sendDelayedTask() {

    }
}
