package io.github.architers.test.task.redis;

import io.github.architers.test.task.*;
import io.github.architers.test.task.constants.ProcessName;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RPriorityBlockingDeque;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.ListAddListener;
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
    public String processName() {
        return ProcessName.REDIS;
    }

    @Override
    public void process(TaskParam taskParam) {
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
        List<RBlockingDeque<TaskParam>> blockingDeques = new ArrayList<>(groups.size());
        for (String group : groups) {
            RPriorityBlockingDeque<TaskParam> rBlockingDeque = redissonClient.getPriorityBlockingDeque(group);
            blockingDeques.add(rBlockingDeque);
            rBlockingDeque.addListener(new ListAddListener() {
                @Override
                public void onListAdd(String name) {
                    System.out.println(name);
                }
            });
        }

        for (RBlockingDeque<TaskParam> blockingDeque : blockingDeques) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        try {
                            TaskParam taskParam = blockingDeque.take();
                            threadPoolExecutor.submit(() -> taskExecutor.executor(taskParam));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {


            }
        }).start();

    }
}
