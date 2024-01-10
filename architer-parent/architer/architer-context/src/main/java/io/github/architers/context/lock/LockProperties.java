package io.github.architers.context.lock;

import io.github.architers.context.lock.eums.LockType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "architers.lock")
@Data
public class LockProperties {

    /**
     * 开启锁
     */
    private boolean enabled;
    /**
     * 默认的锁类型
     */
    private LockType defaultLockType;
}
