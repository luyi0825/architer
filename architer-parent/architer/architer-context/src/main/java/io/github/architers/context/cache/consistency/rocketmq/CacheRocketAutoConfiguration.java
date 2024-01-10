package io.github.architers.context.cache.consistency.rocketmq;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(prefix = "architers.cache.consistency.rocket-mq", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(CacheRocketMqProperties.class)
public class CacheRocketAutoConfiguration {


    @Bean
    public RocketmqCacheOperateInvocationHook rocketmqCacheOperateInvocationHook() {
        return new RocketmqCacheOperateInvocationHook(null);
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
