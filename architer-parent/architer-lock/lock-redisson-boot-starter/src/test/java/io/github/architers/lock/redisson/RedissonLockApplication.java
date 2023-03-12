package io.github.architers.lock.redisson;

import io.github.architers.lock.redisson.expand.NumberCompare;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedissonLockApplication {
    public static void main(String[] args) {
        new SpringApplication(RedissonLockApplication.class).run(args);
    }

    @Bean
    @ConditionalOnMissingBean
    public NumberCompare valueCompare(RedissonClient redissonClient){
        return new NumberCompare(redissonClient);
    }
}
