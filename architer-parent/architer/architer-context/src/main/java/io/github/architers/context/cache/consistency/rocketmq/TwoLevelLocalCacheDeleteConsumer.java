package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.consistency.LocalCacheDelay;
import io.github.architers.context.cache.consistency.LocalCacheDelayDelete;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperateManager;
import io.github.architers.context.cache.operate.LocalAndRemoteCacheOperate;
import lombok.extern.slf4j.Slf4j;
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
@RocketMQMessageListener(topic = "test", consumerGroup = "broadcast_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.BROADCASTING)
@Slf4j
public class TwoLevelLocalCacheDeleteConsumer implements RocketMQListener<MessageExt> {

    @Resource
    private CacheOperateManager cacheOperateManager;

    @Override
    public void onMessage(MessageExt message) {
        String cacheParamName = message.getProperty("cache_param_name");
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        LocalAndRemoteCacheOperate cacheOperate = cacheOperateManager.getCacheOperate(originCacheName);
        if (cacheOperate.getLocalCacheOperate() != null) {
            log.info("删除local缓存:{}", originCacheName);
            CacheChangeParam cacheChangeParam = DeleteCacheUtils.delete(message, cacheOperate.getLocalCacheOperate());
        }


    }


}
