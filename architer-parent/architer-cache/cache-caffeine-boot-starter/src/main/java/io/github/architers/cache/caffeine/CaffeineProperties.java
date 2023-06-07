package io.github.architers.cache.caffeine;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author luyi
 * Caffeine属性配置
 */
@ConfigurationProperties("architer.cahce.caffeine")
@Data
public class CaffeineProperties {

    /**
     * 当没有设置过期时间（也就是-1)的过期时间-默认15年）
     */
    private final Long expireNanosWhenNoSet = (Long.MAX_VALUE >> 1);


    private Map<String, CaffeineConfig> caches;
}
