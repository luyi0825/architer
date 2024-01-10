package io.github.architers.context.cache.consistency;

import io.github.architers.context.cache.consistency.rocketmq.DeleteCacheUtils;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存延迟删除
 */
public class LocalCacheDelayDelete {
    private static final DelayQueue<LocalCacheDelay> delayDelayQueue = new DelayQueue<>();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();


    public static void addDeleteTask(LocalCacheDelay task) {

        delayDelayQueue.add(task);
        executorService.execute(() -> {
            for (; ; ) {
                try {
                    LocalCacheDelay pollLocalCacheDelay = delayDelayQueue.poll(task.getTime() + 100, TimeUnit.MILLISECONDS);
                    if (pollLocalCacheDelay != null) {
                        //删除本地缓存
                        DeleteCacheUtils.deleteLocal(pollLocalCacheDelay.getLocalCacheOperate(), pollLocalCacheDelay.getChangeParam());
                    } else {
                        //延迟队列没数据
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }

}
