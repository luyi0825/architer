package io.github.architers.cache.coherent.support;

import io.github.architers.context.cache.model.CacheOperationParam;
import io.github.architers.context.cache.operate.CacheOperateHook;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class RocketMQCacheOperateHook implements CacheOperateHook {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMQCacheOperateHook(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public void end(CacheOperationParam cacheOperationParam) {
        Message<CacheOperationParam> message = MessageBuilder.withPayload(cacheOperationParam).build();
        rocketMQTemplate.syncSend("xxx", message);
    }
}
