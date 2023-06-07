package io.github.architers.cache.redisson;

import io.github.architers.cache.redisson.RedissonConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * @author luyi
 * redisson缓存的属性配置
 * <li>file和config二选一，两个同时存在优先file</li>
 */
@ConfigurationProperties(prefix = "architers.cache.redisson")
public class RedissonProperties implements Serializable {
    /**
     * 配置的文件(项目下resources下的路径)
     */
    private String file;
    /**
     * 根据属性配置，可以直接配置在yml和properties文件中
     */
    private RedissonConfig config;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public RedissonConfig getConfig() {
        return config;
    }

    public void setConfig(RedissonConfig config) {
        this.config = config;
    }
}
