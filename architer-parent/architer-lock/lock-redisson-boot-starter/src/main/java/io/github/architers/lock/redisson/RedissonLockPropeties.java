package io.github.architers.lock.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson分布式锁属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architer.lock.redisson")
@Data
public class RedissonLockPropeties {

    /**
     * 是否隔离:隔离了就是重新建立redisson连接，默认是
     * <li>解决缓存和锁不是使用的同一套redis环境</li>
     */
    private boolean isolation = true;

    /**
     * 配置的文件(项目下resources下的路径)
     */
    private String file;
    /**
     * 根据属性配置，可以直接配置在yml和properties文件中
     */
    private RedissonLockConfig config;

}
