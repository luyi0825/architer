package com.lz.lock.distributed;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfiguration {
    /**
     * 描述:配置单节点redission
     *
     * @author luyi
     * @date 2020/12/25 下午11:45
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        return Redisson.create(config);
    }

    @Bean
    public LockService lockService() {
        return new DistributedLock();
    }
}
