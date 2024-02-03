package io.github.architers.context.cache.consistency.rocketmq;


import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@ConditionalOnProperty(prefix = "architers.cache.consistency.rocketmq", name = "enabled", havingValue = "true",matchIfMissing = false)
@EnableConfigurationProperties(CacheRocketMqProperties.class)
public class CacheRocketAutoConfiguration {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    public RocketmqCacheOperateInvocationHook rocketmqCacheOperateInvocationHook() {
        return new RocketmqCacheOperateInvocationHook(rocketMQTemplate.getProducer());
    }

    @Bean
    public RocketmqCacheDelayDeleteConsumer rocketmqCacheDelayDeleteConsumer() {
        return new RocketmqCacheDelayDeleteConsumer();
    }

    @Bean
    public TwoLevelLocalCacheDeleteConsumer twoLevelLocalCacheDeleteConsumer() {
        return new TwoLevelLocalCacheDeleteConsumer();
    }
}
