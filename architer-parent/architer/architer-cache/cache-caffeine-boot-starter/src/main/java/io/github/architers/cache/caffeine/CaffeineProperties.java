package io.github.architers.cache.caffeine;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author luyi
 * Caffeine属性配置
 */
@ConfigurationProperties(prefix = "architers.cache.caffeine")
@Data
public class CaffeineProperties {

    /**
     * 当没有设置过期时间（也就是-1)的过期时间-默认10年）
     */
    private final Long expireNanosWhenNoSet = 10L * 365L * 24L * 60L * 60L * 1000L * 1000L;


    private Map<String, CaffeineConfig> caches;
}
