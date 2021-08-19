package com.architectrue.lock.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "customize.redisson.lock")
public class RedissonLockProperties {
    private boolean userConfig = true;
}
