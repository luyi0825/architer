package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.consistency.LocalCacheDelay;
import io.github.architers.context.cache.consistency.LocalCacheDelayDelete;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.cache.operate.LocalAndRemoteCacheOperate;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * rocketmq延迟删除
 *
 * @author luyi
 */
@RocketMQMessageListener(topic = "${architers.cache.value-change-route-key:}_broadcast", consumerGroup = "broadcast_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.BROADCASTING)
public class TwoLevelLocalCacheDeleteConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private CacheOperateSupport cacheOperateSupport;

    @Override
    public void onMessage(MessageExt message) {
        String cacheParamName = message.getProperty("cache_param_name");
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        LocalAndRemoteCacheOperate cacheOperate = (LocalAndRemoteCacheOperate) cacheOperateSupport.getCacheOperate(originCacheName);
        CacheChangeParam cacheChangeParam = DeleteCacheUtils.delete(message, cacheOperate.getLocalCacheOperate());
        if (cacheChangeParam != null) {
            LocalCacheDelay localCacheDelay = new LocalCacheDelay(5000, cacheChangeParam, cacheOperate.getLocalCacheOperate());
            LocalCacheDelayDelete.addDeleteTask(localCacheDelay);
        }


    }


}
