package io.github.architers.cache.consistency.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rocketmq延迟删除配置类
 */
@Configuration
public class RocketDelayDoubleDeleteConfiguration {

    @Bean
    public RocketmqCacheDelayDeleteConsumer rocketmqCacheDelayDeleteConsumer() {
        return new RocketmqCacheDelayDeleteConsumer();
    }

    @Bean
    public RocketmqDelayDoubleDeleteHook rocketmqDelayDoubleDeleteHook(RocketMQTemplate rocketMQTemplate) {
        return new RocketmqDelayDoubleDeleteHook(rocketMQTemplate.getProducer());
    }

    @Bean
    public TwoLevelLocalCacheDeleteConsumer twoLevelLocalCacheDeleteConsumer() {
        return new TwoLevelLocalCacheDeleteConsumer();
    }

}
