package io.github.architers.test.asynctask.redis;

import io.github.architers.test.asynctask.*;
import org.redisson.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Administrator
 */
public class RedisTask extends AbstractTaskExecutor implements TaskSubmit, InitializingBean {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private TaskRegister register;

    @Resource
    private ApplicationContext applicationContext;

    private Map<String, RScheduledExecutorService> rScheduledExecutorServiceMap;

    public RedisTask(TaskRegister taskRegister) {
        super(taskRegister);
    }


    @Override
    public void submit(TaskSendRequest request) {
        RScheduledExecutorService executorService = rScheduledExecutorServiceMap.get(request.getTaskName());
        RExecutorFuture<?> submit = executorService.submit((Runnable & Serializable) () -> {
            this.executor(request);
        });

//        String taskName = request.getTaskName();
//        RBlockingQueue<TaskSendRequest> rBlockingQueue = redissonClient.get(taskName);
//        //  rBlockingQueue.add(request);
//        RPriorityBlockingQueue<TaskSendRequest> rBlockingDeque = redissonClient.getPriorityBlockingQueue(taskName);
//        rBlockingDeque.add(request);
////        rBlockingDeque.take();

    }

    @Override
    public void afterPropertiesSet() {
        Set<String> taskNames = register.getTaskName();
        rScheduledExecutorServiceMap = new HashMap<>();
        for (String taskName : taskNames) {
            RScheduledExecutorService executorService = redissonClient.getExecutorService(taskName);
            executorService.registerWorkers(WorkerOptions.defaults().executorService(threadPoolExecutor).workers(5));
            rScheduledExecutorServiceMap.put(taskName, executorService);
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(2000);
//                        for (RScheduledExecutorService value : rScheduledExecutorServiceMap.values()) {
//                            System.out.println(value.countActiveWorkers());
//
//                        }
//                        System.out.println(threadPoolExecutor.toString());
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }).start();

    }

}
