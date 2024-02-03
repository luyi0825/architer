package io.github.architers.context.cache.consistency.rocketmq;


import com.alibaba.fastjson.JSON;
import io.github.architers.common.json.JsonUtils;
import io.github.architers.context.cache.operate.CacheOperate;
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
 * rocketmq延迟删除通知
 *
 * @author luyi
 */
@RocketMQMessageListener(topic = "test", consumerGroup = "clustering_consumer", selectorExpression = "${spring.application.name:}", messageModel = MessageModel.CLUSTERING)
@Slf4j
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
        if (localAndRemoteCacheOperate.getRemoteCacheOperate() != null) {
            log.info("删除remote缓存:{}", JsonUtils.toJsonString(message.getBody()));
            DeleteCacheUtils.delete(message, localAndRemoteCacheOperate.getRemoteCacheOperate());
        }
    }


}
