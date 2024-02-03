package io.github.architers.context.cache.consistency.rocketmq;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.consistency.LocalCacheDelay;
import io.github.architers.context.cache.consistency.LocalCacheDelayDelete;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.operate.*;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
import io.github.architers.common.json.JsonUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private CacheRocketMqProperties cacheRocketMqProperties;


    private final DefaultMQProducer producer;

    public RocketmqCacheOperateInvocationHook(DefaultMQProducer producer) {
        this.producer = producer;
    }

    @Override
    public boolean before(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        if (!(cacheParam instanceof CacheChangeParam)) {
            return true;
        }
        CacheConfig cacheConfig = this.getCacheConfig(cacheParam.getOriginCacheName());
        if (Boolean.TRUE.equals(cacheConfig.getChangeDelayDeleteAgain())) {
            //发送延迟删消息
            SendResult sendResult;
            try {
                Message message = new Message();
                //一级远程缓存
                long delayTimeMs = TimeUnit.MILLISECONDS.convert(cacheConfig.getDelayDeleteTime(), cacheConfig.getExpireUnit());
                message.setDelayTimeMs(delayTimeMs);
                message.setTopic(cacheRocketMqProperties.getTopic());
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
        return true;
    }

    private CacheConfig getCacheConfig(String originCacheName) {
        CacheConfig cacheConfig = cacheProperties.getCustomConfigs().get(originCacheName);
        if (cacheConfig != null) {
            return cacheConfig;
        }
        //定制的不存在就使用公共的配置
        cacheConfig = cacheProperties.getCommonConfig();
        if (cacheConfig == null) {
            throw new IllegalArgumentException("缓存配置不存在:" + originCacheName);
        }
        return cacheConfig;
    }


    @Override
    public void after(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
//        if (cacheOperate instanceof LocalCacheOperate) {
//            if (!(cacheParam instanceof CacheChangeParam)) {
//                return ;
//            }
//            LocalCacheDelay localCacheDelay = new LocalCacheDelay(5000, (CacheChangeParam) cacheParam, (LocalCacheOperate) cacheOperate);
//            LocalCacheDelayDelete.addDeleteTask(localCacheDelay);
//        }
    }
}
