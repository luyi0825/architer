package io.github.architers.cache.redisson;

import io.github.architers.propertconfig.redisson.RedissonBaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * redisson缓存的属性配置
 *
 * @author luyi
 * @since 1.0.2
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = RedissonProperties.prefix)
@Data
public class RedissonProperties extends RedissonBaseProperties implements Serializable {

    public static final String prefix = "architers.cache.redisson";

}
