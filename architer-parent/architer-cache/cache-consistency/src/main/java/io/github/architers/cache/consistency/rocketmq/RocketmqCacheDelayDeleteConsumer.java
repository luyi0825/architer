package io.github.architers.cache.consistency.rocketmq;


import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.cache.operate.TwoLevelCacheOperate;
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
        String originCacheName = message.getProperty("origin_cache_name");
        if (!StringUtils.hasText(originCacheName)) {
            return;
        }
        CacheOperate cacheOperate = cacheOperateSupport.getCacheOperate(originCacheName);
        if (cacheOperate instanceof TwoLevelCacheOperate) {
            //两级缓存删除远程分布式缓存
            DeleteCacheUtils.delete(message, ((TwoLevelCacheOperate) cacheOperate).getRemoteCacheOperate());
        } else {
            //不是两级缓存，直接删除
            DeleteCacheUtils.delete(message, cacheOperate);
        }

    }


}
