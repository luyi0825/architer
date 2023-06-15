package io.github.architers.cache.consistency.rocketmq;

import io.github.architers.context.cache.CacheConstants;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 删除缓存工具类
 *
 * @author luyi
 */
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
            EvictParam evictParam = new EvictParam();
            evictParam.setOriginCacheName(putParam.getOriginCacheName());
            evictParam.setWrapperCacheName(putParam.getWrapperCacheName());
            evictParam.setKey(putParam.getKey());
            changeParam = evictParam;
            cacheOperate.delete(evictParam);
        } else if (EvictParam.class.getSimpleName().equals(cacheParamName)) {
            EvictParam evictParam = JsonUtils.readValue(message.getBody(), EvictParam.class);
            cacheOperate.delete(evictParam);
            changeParam = evictParam;
        } else if (BatchEvictParam.class.getSimpleName().equals(cacheParamName)) {
            BatchEvictParam batchEvictParam = JsonUtils.readValue(message.getBody(), BatchEvictParam.class);
            cacheOperate.batchDelete(batchEvictParam);
            changeParam = batchEvictParam;
        } else if (BatchPutParam.class.getSimpleName().equals(cacheParamName)) {
            BatchPutParam batchPutParam = JsonUtils.readValue(message.getBody(), BatchPutParam.class);
            BatchEvictParam batchEvictParam = new BatchEvictParam();
            batchEvictParam.setOriginCacheName(batchPutParam.getOriginCacheName());
            batchEvictParam.setWrapperCacheName(batchEvictParam.getWrapperCacheName());
            Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), CacheConstants.CACHE_SPLIT);
            batchEvictParam.setKeys(cacheMap.keySet());
            cacheOperate.batchDelete(batchEvictParam);
            changeParam = batchEvictParam;
        } else if (EvictAllParam.class.getSimpleName().equals(cacheParamName)) {
            EvictAllParam evictAllParam = JsonUtils.readValue(message.getBody(), EvictAllParam.class);
            cacheOperate.deleteAll(evictAllParam);
            changeParam = evictAllParam;
        } else {
            throw new RuntimeException("删除本地缓存失败");
        }
        return changeParam;

    }


}
