package io.github.architers.context.cache.operate.support;

import io.github.architers.context.cache.enums.CacheType;
import io.github.architers.context.cache.model.CacheOperationParam;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.ICacheChangeNotify;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * rocketmq延迟删除通知
 *
 * @author luyi
 */
public class RocketmqCacheChangeNotify implements ICacheChangeNotify {

    private final DefaultMQProducer producer;

    public RocketmqCacheChangeNotify(DefaultMQProducer producer) {
        this.producer = producer;
    }

    @Override
    public void notify(CacheOperate cacheOperate, CacheOperationParam cacheOperationParam) {

        SendResult sendResult;
        Message message = new Message();
        try {
            //一级远程缓存
            message.setDelayTimeMs(1500);
            message.setTopic("xxx");
            message.setTags("test");
            //缓存类型
            message.putUserProperty("cache_type", CacheType.getCacheType(cacheOperate).name());
            message.putUserProperty("cache_param_name", cacheOperationParam.getClass().getSimpleName());
            message.setBody(JsonUtils.toJsonBytes(cacheOperationParam));
            sendResult = producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("操作失败，请重试");
        }
    }
}
