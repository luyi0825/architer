package io.github.architers.context.cache.consistency.rocketmq;


import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.CacheOperateManager;
import io.github.architers.context.cache.operate.LocalAndRemoteCacheOperate;
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
    private CacheOperateManager cacheOperateManager;

    @Override
    public void onMessage(MessageExt message) {
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        LocalAndRemoteCacheOperate localAndRemoteCacheOperate = cacheOperateManager.getCacheOperate(originCacheName);
        //TODO remote缓存重复删除
        if (localAndRemoteCacheOperate.getRemoteCacheOperate() != null) {
            DeleteCacheUtils.delete(message, localAndRemoteCacheOperate.getRemoteCacheOperate());
        }
        if (localAndRemoteCacheOperate.getLocalCacheOperate() != null) {
            //不是两级缓存，直接删除
            DeleteCacheUtils.delete(message, localAndRemoteCacheOperate.getLocalCacheOperate());
        }
    }


}
