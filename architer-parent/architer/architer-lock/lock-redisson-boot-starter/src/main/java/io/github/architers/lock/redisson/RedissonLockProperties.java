package io.github.architers.lock.redisson;


import io.github.architers.propertconfig.redisson.RedissonBaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.github.architers.lock.redisson.RedissonLockProperties.PREFIX;

/**
 * redisson分布式锁属性配置类
 *
 * @author lyi
 * @since 1.0.3
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = PREFIX)
@Data
public class RedissonLockProperties extends RedissonBaseProperties {

    public static final String PREFIX = "architers.lock.redisson";

    /**
     * 是否隔离（如果为true,redisson的锁对应的redissonClient连接就和其他不是同一个）
     * <li>默认false</li>
     */
    private Boolean isolation = Boolean.FALSE;


}
