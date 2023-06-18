package io.github.architers.cache.consistency.rocketmq;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.annotation.CacheBatchEvict;
import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.annotation.CacheEvictAll;
import io.github.architers.context.cache.consistency.CacheDeleteUtils;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.operate.*;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
import io.github.architers.context.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
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
 * rocketmq延迟删除
 *
 * @author luyi
 * @since 1.0.1
 */
@Slf4j
public class RocketmqDelayDoubleDeleteHook implements CacheOperateInvocationHook {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private CacheProperties cacheProperties;

    private final DefaultMQProducer producer;

    public RocketmqDelayDoubleDeleteHook(DefaultMQProducer producer) {
        this.producer = producer;
    }

    @Override
    public boolean before(BaseCacheParam cacheParam, CacheOperateContext cacheOperateContext) {
        if (!delayEvict(cacheParam, cacheOperateContext)) {
            throw new RuntimeException("操作失败，请重试!");
        }
        return true;

    }

    public boolean delayEvict(BaseCacheParam cacheParam, CacheOperateContext cacheOperateContext) {
        if (!(cacheParam instanceof CacheChangeParam)) {
            return true;
        }
        boolean delayEvictAgain = cacheOperateContext.isDelayEvictAgain();
        if (!delayEvictAgain) {
            return true;
        }
        if (cacheParam instanceof CacheEvictAll || cacheParam instanceof CacheEvict || cacheParam instanceof CacheBatchEvict) {
            throw new RuntimeException("当delayEvict为true,只能使用缓存驱逐的注解");
        }
        CacheOperate cacheOperate = cacheOperateContext.getCacheOperate();
        try {
            if (cacheOperate instanceof TwoLevelCacheOperate) {
                //发送广播消息删除所有的本地缓存
                this.sendEvictLocalCacheBroadcastMessage(cacheParam, 0);
                //删除远程的消息
                this.sendEvictRemoteCacheMessage(cacheParam, cacheOperateContext);
            } else if (cacheOperate instanceof RemoteCacheOperate) {
                this.sendEvictRemoteCacheMessage(cacheParam, cacheOperateContext);
            } else {
                if (cacheOperate instanceof LocalCacheOperate) {
                    //延迟删除本地
                    CacheDeleteUtils.delayDelete(cacheOperateContext.getDelayEvictMills(), (CacheChangeParam) cacheParam, (LocalCacheOperate) cacheOperateContext.getCacheOperate());
                }
            }
        } catch (Exception e) {
            log.error("延迟删除缓存失败", e);
            return false;
        }
        return true;
    }

    private void sendEvictLocalCacheBroadcastMessage(BaseCacheParam cacheParam, int delayTimeMs) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        SendResult sendResult;
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
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("发送消息失败");
        }
    }

    private void sendEvictRemoteCacheMessage(BaseCacheParam cacheParam, CacheOperateContext cacheOperateContext) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //发送延迟删消息
        SendResult sendResult;
        Message message = new Message();
        //一级远程缓存
        message.setDelayTimeMs(cacheOperateContext.getDelayEvictMills());
        message.setTopic(cacheProperties.getValueChangeRouteKey());
        message.setTags(applicationName);
        message.putUserProperty("origin_cache_name", cacheParam.getOriginCacheName());
        //缓存参数名称
        message.putUserProperty("cache_param_name", cacheParam.getClass().getSimpleName());
        message.setBody(JsonUtils.toJsonBytes(cacheParam));
        sendResult = producer.send(message);


        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.error("发送消息失败:{}", sendResult);
        }
    }


    @Override
    public void after(BaseCacheParam cacheParam, CacheOperateContext cacheOperateContext) {
        if (cacheOperateContext.isDelayEvictAgain() && cacheParam instanceof CacheChangeParam) {
            throw new IllegalArgumentException("当delayEvict为true,beforeInvocation必须为true!");
        }

    }
}
