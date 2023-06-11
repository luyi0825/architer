package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.consistency.LocalCacheDelay;
import io.github.architers.context.cache.consistency.LocalCacheDelayDelete;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.util.Map;

public class DeleteCacheUtils {

    public static CacheChangeParam delete(MessageExt message, CacheOperate cacheOperate) {
        String cacheParamName = message.getProperty("cache_param_name");
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return null;
        }
        CacheChangeParam changeParam;
        if (PutParam.class.getSimpleName().equals(cacheParamName)) {
            PutParam putParam = JsonUtils.readValue(message.getBody(), PutParam.class);
            DeleteParam deleteParam = new DeleteParam();
            deleteParam.setOriginCacheName(putParam.getOriginCacheName());
            deleteParam.setWrapperCacheName(putParam.getWrapperCacheName());
            deleteParam.setKey(putParam.getKey());
            changeParam = deleteParam;
            cacheOperate.delete(deleteParam);
        } else if (DeleteParam.class.getSimpleName().equals(cacheParamName)) {
            DeleteParam deleteParam = JsonUtils.readValue(message.getBody(), DeleteParam.class);
            cacheOperate.delete(deleteParam);
            changeParam = deleteParam;
        } else if (BatchDeleteParam.class.getSimpleName().equals(cacheParamName)) {
            BatchDeleteParam batchDeleteParam = JsonUtils.readValue(message.getBody(), BatchDeleteParam.class);
            cacheOperate.batchDelete(batchDeleteParam);
            changeParam = batchDeleteParam;
        } else if (BatchPutParam.class.getSimpleName().equals(cacheParamName)) {
            BatchPutParam batchPutParam = JsonUtils.readValue(message.getBody(), BatchPutParam.class);
            BatchDeleteParam batchDeleteParam = new BatchDeleteParam();
            batchDeleteParam.setOriginCacheName(batchPutParam.getOriginCacheName());
            batchDeleteParam.setWrapperCacheName(batchDeleteParam.getWrapperCacheName());
            Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), Symbol.COLON);
            batchDeleteParam.setKeys(cacheMap.keySet());
            cacheOperate.batchDelete(batchDeleteParam);
            changeParam = batchDeleteParam;
        } else if (DeleteAllParam.class.getSimpleName().equals(cacheParamName)) {
            DeleteAllParam deleteAllParam = JsonUtils.readValue(message.getBody(), DeleteAllParam.class);
            cacheOperate.deleteAll(deleteAllParam);
            changeParam = deleteAllParam;
        } else {
            throw new RuntimeException("删除本地缓存失败");
        }
        return changeParam;

    }

    public static void deleteLocal(LocalCacheOperate localCacheOperate, CacheChangeParam param) {
        if (param instanceof PutParam) {
            PutParam putParam = (PutParam) param;
            DeleteParam deleteParam = new DeleteParam();
            deleteParam.setOriginCacheName(putParam.getOriginCacheName());
            deleteParam.setWrapperCacheName(putParam.getWrapperCacheName());
            deleteParam.setKey(putParam.getKey());
            localCacheOperate.delete(deleteParam);
            return;
        }
        if (param instanceof DeleteParam) {
            DeleteParam deleteParam = (DeleteParam) param;
            localCacheOperate.delete(deleteParam);
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
}
