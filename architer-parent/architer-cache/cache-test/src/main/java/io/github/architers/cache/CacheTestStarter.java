package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableArchiterCaching;
import io.github.architers.context.cache.consistency.rocketmq.RocketmqCacheDelayDeleteConsumer;
import io.github.architers.context.cache.consistency.rocketmq.RocketmqCacheOperateInvocationHook;
import io.github.architers.context.cache.consistency.rocketmq.TwoLevelLocalCacheDeleteConsumer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableArchiterCaching(proxyTargetClass = true)
public class CacheTestStarter {
    public static void main(String[] args) {
        SpringApplication.run(CacheTestStarter.class, args);
    }


    @Bean("rocketmqCacheChangeNotify_test")
    public RocketmqCacheOperateInvocationHook rocketmqCacheChangeNotify(RocketMQTemplate rocketMQTemplate) {
        return new RocketmqCacheOperateInvocationHook(rocketMQTemplate.getProducer());
    }

    @Bean
    public RocketmqCacheDelayDeleteConsumer rocketmqCacheChangeNotifyConsumer(){
        return new RocketmqCacheDelayDeleteConsumer();
    }

    @Bean
    public TwoLevelLocalCacheDeleteConsumer rocketmqDeleteLocalCacheConsumer() {
        return new TwoLevelLocalCacheDeleteConsumer();
    }
}
