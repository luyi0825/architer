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
public class RedisTaskProcess implements TaskProcess, SmartInitializingSingleton {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private TaskConsumerTargetRegister register;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private DefaultTaskExecutor taskExecutor;

    @Override
    public String processName() {
        return ProcessName.REDIS;
    }

    @Override
    public void process(SendParam sendParam) {
        RBlockingDeque<SendParam> blockingDeque = redissonClient.getPriorityBlockingDeque(sendParam.getGroup());
        //发送延迟任务
        if (sendParam instanceof DelayedTaskParam) {
            RDelayedQueue<SendParam> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(sendParam, ((DelayedTaskParam) sendParam).getDelayedTime(), ((DelayedTaskParam) sendParam).getTimeUnit());
            return;
        }
        //发送即时任务
        blockingDeque.add(sendParam);
    }

    @Override
    public void afterSingletonsInstantiated() {
        Set<String> groups = register.getTaskGroups();
        List<RBlockingDeque<SendParam>> blockingDeques = new ArrayList<>(groups.size());
        for (String group : groups) {
            RPriorityBlockingDeque<SendParam> rBlockingDeque = redissonClient.getPriorityBlockingDeque(group);
            blockingDeques.add(rBlockingDeque);
            rBlockingDeque.addListener(new ListAddListener() {
                @Override
                public void onListAdd(String name) {
                    System.out.println(name);
                }
            });
        }

        for (RBlockingDeque<SendParam> blockingDeque : blockingDeques) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        try {
                            SendParam sendParam = blockingDeque.take();
                            threadPoolExecutor.submit(() -> taskExecutor.executor(sendParam));
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
