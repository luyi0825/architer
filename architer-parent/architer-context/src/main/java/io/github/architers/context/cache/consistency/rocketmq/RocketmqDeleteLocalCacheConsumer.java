package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.cache.operate.TwoLevelCacheOperate;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * rocketmq延迟删除
 *
 * @author luyi
 */
@RocketMQMessageListener(topic = "${architers.cache.value-change-route-key:}", consumerGroup = "clustering_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.BROADCASTING)
public class RocketmqDeleteLocalCacheConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private CacheOperateSupport cacheOperateSupport;

    @Override
    public void onMessage(MessageExt message) {
        String cacheParamName = message.getProperty("cache_param_name");
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        TwoLevelCacheOperate cacheOperate = (TwoLevelCacheOperate) cacheOperateSupport.getCacheOperate(originCacheName);

        if (PutParam.class.getSimpleName().equals(cacheParamName)) {
            PutParam putParam = JsonUtils.readValue(message.getBody(), PutParam.class);
            DeleteParam deleteParam = new DeleteParam();
            deleteParam.setOriginCacheName(putParam.getOriginCacheName());
            deleteParam.setWrapperCacheName(putParam.getWrapperCacheName());
            deleteParam.setKey(putParam.getKey());
            cacheOperate.getLocalCacheOperate().delete(deleteParam);
            return;
        }
        if (DeleteParam.class.getSimpleName().equals(cacheParamName)) {
            DeleteParam deleteParam = JsonUtils.readValue(message.getBody(), DeleteParam.class);
            cacheOperate.getLocalCacheOperate().delete(deleteParam);
            return;
        }
        if (BatchDeleteParam.class.getSimpleName().equals(cacheParamName)) {
            BatchDeleteParam batchDeleteParam = JsonUtils.readValue(message.getBody(), BatchDeleteParam.class);
            cacheOperate.getLocalCacheOperate().batchDelete(batchDeleteParam);
            return;
        }
        if (BatchPutParam.class.getSimpleName().equals(cacheParamName)) {
            BatchPutParam batchPutParam = JsonUtils.readValue(message.getBody(), BatchPutParam.class);
            BatchDeleteParam batchDeleteParam = new BatchDeleteParam();
            batchDeleteParam.setOriginCacheName(batchPutParam.getOriginCacheName());
            batchDeleteParam.setWrapperCacheName(batchDeleteParam.getWrapperCacheName());
            Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), Symbol.COLON);
            batchDeleteParam.setKeys(cacheMap.keySet());
            cacheOperate.getLocalCacheOperate().batchDelete(batchDeleteParam);
            return;
        }
        if (DeleteAllParam.class.getSimpleName().equals(cacheParamName)) {
            DeleteAllParam deleteAllParam = JsonUtils.readValue(message.getBody(), DeleteAllParam.class);
            cacheOperate.getLocalCacheOperate().deleteAll(deleteAllParam);
        }
        throw new RuntimeException("删除本地缓存失败");


    }


}
