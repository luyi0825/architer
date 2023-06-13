package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.consistency.LocalCacheDelay;
import io.github.architers.context.cache.consistency.LocalCacheDelayDelete;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.operate.*;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
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
public class RocketmqCacheOperateInvocationHook implements CacheOperateInvocationHook {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private CacheProperties cacheProperties;


    private final DefaultMQProducer producer;

    public RocketmqCacheOperateInvocationHook(DefaultMQProducer producer) {
        this.producer = producer;
    }


    private void sendDeleteLocalBroadcastMessage(BaseCacheParam cacheParam, long delayTimeMs) {
        SendResult sendResult;
        try {
            Message message = new Message();
            //一级远程缓存
            if (delayTimeMs > 0) {
                message.setDelayTimeMs(delayTimeMs);
            }
            //一级远程缓存
            message.setTopic(cacheProperties.getValueChangeRouteKey() + "_broadcast");
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
    public boolean before(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        if (!(cacheParam instanceof CacheChangeParam)) {
            return true;
        }
        if (cacheOperate instanceof TwoLevelCacheOperate) {
            //两级缓存，发送广播消息删除所有的本地缓存
            this.sendDeleteLocalBroadcastMessage(cacheParam, 0);
            return true;
        }
        if (cacheOperate instanceof RemoteCacheOperate) {
            CacheConfig cacheConfig = cacheProperties.getCustomConfigs().get(cacheParam.getOriginCacheName());
            Boolean changeDelayDelete;

            if (cacheConfig != null) {
                changeDelayDelete = cacheConfig.getChangeDelayDelete();
            } else {
                changeDelayDelete = cacheProperties.isChangeDelayDelete();
            }
            if (Boolean.TRUE.equals(changeDelayDelete)) {
                //发送延迟删消息
                SendResult sendResult;
                try {
                    Message message = new Message();
                    //一级远程缓存
                    message.setDelayTimeMs(3000);
                    message.setTopic(cacheProperties.getValueChangeRouteKey());
                    message.setTags(applicationName);
                    message.putUserProperty("origin_cache_name", cacheParam.getOriginCacheName());
                    //缓存参数名称
                    message.putUserProperty("cache_param_name", cacheParam.getClass().getSimpleName());
                    message.setBody(JsonUtils.toJsonBytes(cacheParam));
                    sendResult = producer.send(message);
                } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                    return false;
                }
                return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
            }
        }
        return true;
    }


    @Override
    public void after(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        if (cacheOperate instanceof LocalCacheOperate) {
            if (!(cacheParam instanceof CacheChangeParam)) {
                return ;
            }
            LocalCacheDelay localCacheDelay = new LocalCacheDelay(5000, (CacheChangeParam) cacheParam, (LocalCacheOperate) cacheOperate);
            LocalCacheDelayDelete.addDeleteTask(localCacheDelay);
        }
    }
}
