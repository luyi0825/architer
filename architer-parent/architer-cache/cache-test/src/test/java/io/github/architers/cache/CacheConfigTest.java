package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableArchiterCaching;
import io.github.architers.context.cache.operate.support.RocketmqCacheChangeNotify;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableArchiterCaching(proxyTargetClass = true)
public class CacheConfigTest {
    public static void main(String[] args) {
        SpringApplication.run(CacheConfigTest.class, args);
    }

    @Bean("rocketmqCacheChangeNotify_test")
    public RocketmqCacheChangeNotify rocketmqCacheChangeNotify(RocketMQTemplate rocketMQTemplate) {
        return new RocketmqCacheChangeNotify(rocketMQTemplate.getProducer());
    }
}
