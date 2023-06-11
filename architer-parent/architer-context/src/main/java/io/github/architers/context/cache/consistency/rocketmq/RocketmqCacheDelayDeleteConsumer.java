package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.model.DeleteParam;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * rocketmq延迟删除通知
 *
 * @author luyi
 */
@RocketMQMessageListener(topic = "${architers.cache.value-change-route-key:}", consumerGroup = "clustering_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.CLUSTERING)
public class RocketmqCacheDelayDeleteConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private CacheOperateSupport cacheOperateSupport;

    @Override
    public void onMessage(MessageExt message) {
        String cacheParamName = message.getProperty("cache_param_name");
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        CacheOperate cacheOperate = cacheOperateSupport.getCacheOperate(originCacheName);

        if (PutParam.class.getSimpleName().equals(cacheParamName)) {
            PutParam putParam = JsonUtils.readValue(message.getBody(), PutParam.class);
            DeleteParam deleteParam = new DeleteParam();
            deleteParam.setOriginCacheName(putParam.getOriginCacheName());
            deleteParam.setWrapperCacheName(putParam.getWrapperCacheName());
            deleteParam.setKey(putParam.getKey());
            cacheOperate.delete(deleteParam);
        }
    }


}
