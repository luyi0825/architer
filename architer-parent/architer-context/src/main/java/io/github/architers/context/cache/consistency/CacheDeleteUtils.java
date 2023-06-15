package io.github.architers.context.cache.consistency;

import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.model.EvictParam;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.operate.LocalCacheOperate;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存删除工具类
 *
 * @author luyi
 * @since 1.0.1
 */
public final class CacheDeleteUtils {

    private static final DelayQueue<LocalDelayDeleteTask> delayDelayQueue = new DelayQueue<>();

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 直接删
     */
    public static void directDelete(LocalCacheOperate localCacheOperate, CacheChangeParam param) {

        if (param instanceof EvictParam) {
            EvictParam evictParam = (EvictParam) param;
            localCacheOperate.delete(evictParam);
            return;
        }
        if (param instanceof PutParam) {
            PutParam putParam = (PutParam) param;
            EvictParam evictParam = new EvictParam();
            evictParam.setOriginCacheName(putParam.getOriginCacheName());
            evictParam.setWrapperCacheName(putParam.getWrapperCacheName());
            evictParam.setKey(putParam.getKey());
            localCacheOperate.delete(evictParam);
            return;
        }

//        if (BatchDeleteParam.class.getSimpleName().equals(cacheParamName)) {
//            BatchDeleteParam batchDeleteParam = JsonUtils.readValue(message.getBody(), BatchDeleteParam.class);
//            cacheOperate.batchDelete(batchDeleteParam);
//            return;
//        }
//        if (BatchPutParam.class.getSimpleName().equals(cacheParamName)) {
//            BatchPutParam batchPutParam = JsonUtils.readValue(message.getBody(), BatchPutParam.class);
//            BatchDeleteParam batchDeleteParam = new BatchDeleteParam();
//            batchDeleteParam.setOriginCacheName(batchPutParam.getOriginCacheName());
//            batchDeleteParam.setWrapperCacheName(batchDeleteParam.getWrapperCacheName());
//            Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), Symbol.COLON);
//            batchDeleteParam.setKeys(cacheMap.keySet());
//            cacheOperate.batchDelete(batchDeleteParam);
//            return;
//        }
//        if (DeleteAllParam.class.getSimpleName().equals(cacheParamName)) {
//            DeleteAllParam deleteAllParam = JsonUtils.readValue(message.getBody(), DeleteAllParam.class);
//            cacheOperate.deleteAll(deleteAllParam);
//        }
        throw new RuntimeException("删除本地缓存失败");
    }

    /**
     * 延迟删
     */
    public static void delayDelete(long delayTimeMills, CacheChangeParam changeParam, LocalCacheOperate localCacheOperate) {

        LocalDelayDeleteTask localDelayDeleteTask = new LocalDelayDeleteTask(delayTimeMills, changeParam, localCacheOperate);
        delayDelayQueue.add(localDelayDeleteTask);
        executorService.execute(() -> {
            for (; ; ) {
                try {
                    LocalDelayDeleteTask pollLocalDelayDeleteTask = delayDelayQueue.poll(localDelayDeleteTask.getTime() + 100, TimeUnit.MILLISECONDS);
                    if (pollLocalDelayDeleteTask != null) {
                        //删除本地缓存
                        CacheDeleteUtils.directDelete(pollLocalDelayDeleteTask.getLocalCacheOperate(), pollLocalDelayDeleteTask.getChangeParam());
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
