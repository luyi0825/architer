package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableCaching;
import io.github.architers.context.cache.consistency.rocketmq.RocketmqCacheDelayDeleteConsumer;
import io.github.architers.context.cache.consistency.rocketmq.RocketmqCacheOperateInvocationHook;
import io.github.architers.context.cache.consistency.rocketmq.TwoLevelLocalCacheDeleteConsumer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true,mode = AdviceMode.PROXY)
public class CacheTestStarter {
    public static void main(String[] args) {
        SpringApplication.run(CacheTestStarter.class, args);
    }

}
