package io.github.architers.test.task.redis;

import io.github.architers.test.task.*;
import io.github.architers.context.task.constants.SenderName;
import io.github.architers.context.task.subscriber.DefaultTaskExecutor;
import io.github.architers.context.task.subscriber.TaskSubscriberTargetScanner;
import io.github.architers.context.task.send.DelayedTaskParam;
import io.github.architers.context.task.send.TaskParam;
import io.github.architers.context.task.send.TaskSender;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RPriorityBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luyi
 */
@Component
public class RedisTaskSender implements TaskSender, SmartInitializingSingleton {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private TaskSubscriberTargetScanner register;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private DefaultTaskExecutor taskExecutor;



    @Override
    public String senderName() {
        return SenderName.REDIS;
    }

    @Override
    public void doSend(TaskParam taskParam) {
        RBlockingDeque<TaskParam> blockingDeque = redissonClient.getPriorityBlockingDeque(taskParam.getGroup());
        //发送延迟任务
        if (taskParam instanceof DelayedTaskParam) {
            RDelayedQueue<TaskParam> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(taskParam, ((DelayedTaskParam) taskParam).getDelayedTime(), ((DelayedTaskParam) taskParam).getTimeUnit());
            return;
        }
        //发送即时任务
        blockingDeque.add(taskParam);
    }

    @Override
    public void afterSingletonsInstantiated() {
        Set<String> groups = register.getTaskGroups();
        List<RBlockingDeque<TaskStore>> blockingDeques = new ArrayList<>(groups.size());
        for (String group : groups) {
            RPriorityBlockingDeque<TaskStore> rBlockingDeque = redissonClient.getPriorityBlockingDeque(group);
            blockingDeques.add(rBlockingDeque);
        }

        for (RBlockingDeque<TaskStore> blockingDeque : blockingDeques) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        TaskStore taskStore = blockingDeque.take();
                        threadPoolExecutor.submit(() -> taskExecutor.executor(taskStore));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }


    }
}
