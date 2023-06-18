package io.github.architers.cache.consistency.rocketmq;

import io.github.architers.context.cache.consistency.CacheDeleteUtils;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperateContext;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.cache.operate.TwoLevelCacheOperate;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * rocketmq延迟删除
 *
 * @author luyi
 */
@Lazy(value = false)
@RocketMQMessageListener(topic = "${architers.cache.value-change-route-key:}_broadcast", consumerGroup = "broadcast_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.BROADCASTING)
public class TwoLevelLocalCacheDeleteConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private CacheOperateSupport cacheOperateSupport;

    @Override
    public void onMessage(MessageExt message) {
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        CacheOperateContext cacheOperateContext = cacheOperateSupport.getCacheOperateContext(originCacheName);
        TwoLevelCacheOperate cacheOperate = (TwoLevelCacheOperate) cacheOperateContext.getCacheOperate();
        CacheChangeParam cacheChangeParam = DeleteCacheUtils.delete(message, cacheOperate.getLocalCacheOperate());
        if (cacheChangeParam != null && cacheOperateContext.isDelayEvictAgain()) {
            CacheDeleteUtils.delayDelete(cacheOperateContext.getDelayEvictMills(), cacheChangeParam, cacheOperate.getLocalCacheOperate());
        }


    }


}
