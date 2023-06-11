package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.CacheOperateEndHook;
import io.github.architers.context.cache.operate.TwoLevelCacheOperate;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * rocketmq延迟删除通知
 *
 * @author luyi
 */
public class RocketmqCacheOperateEndHook implements CacheOperateEndHook {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private CacheProperties cacheProperties;


    private final DefaultMQProducer producer;

    public RocketmqCacheOperateEndHook(DefaultMQProducer producer) {
        this.producer = producer;
    }


    private void sendDeleteLocalBroadcastMessage(BaseCacheParam cacheParam) {
        SendResult sendResult;
        try {
            Message message = new Message();
            //一级远程缓存
            message.setTopic(cacheProperties.getValueChangeRouteKey());
            message.setTags(applicationName);
            message.putUserProperty("origin_cache_name", cacheParam.getOriginCacheName());
            //缓存参数名称
            message.putUserProperty("cache_param_name", cacheParam.getClass().getSimpleName());
            message.setBody(JsonUtils.toJsonBytes(cacheParam));
            sendResult = producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("操作失败，请重试");
        }
    }


    private void sendChangeDelayDeleteMessage(CacheOperate cacheOperate, BaseCacheParam cacheParam) {
        SendResult sendResult;
        try {
            Message message = new Message();
            //一级远程缓存
            message.setDelayTimeMs(1500);
            message.setTopic(cacheProperties.getValueChangeRouteKey());
            message.setTags(applicationName);
            message.putUserProperty("origin_cache_name", cacheParam.getOriginCacheName());
            //缓存参数名称
            message.putUserProperty("cache_param_name", cacheParam.getClass().getSimpleName());
            message.setBody(JsonUtils.toJsonBytes(cacheParam));
            sendResult = producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("操作失败，请重试");
        }
    }

    @Override
    public void end(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        CacheConfig cacheConfig = cacheProperties.getCustomConfigs().get(cacheParam.getOriginCacheName());
        Boolean changeDelayDelete;

        if (cacheOperate instanceof TwoLevelCacheOperate) {
            this.sendDeleteLocalBroadcastMessage(cacheParam);
            return;
        }

        if (cacheParam instanceof CacheChangeParam) {
            if (cacheConfig != null) {
                changeDelayDelete = cacheConfig.getChangeDelayDelete();
            } else {
                changeDelayDelete = cacheProperties.isChangeDelayDelete();
            }
            if (Boolean.TRUE.equals(changeDelayDelete)) {
                //发送延迟删消息
                this.sendChangeDelayDeleteMessage(cacheOperate, cacheParam);
            }
        }
    }
}
