package com.architecture.ultimate.cache.lock.distributed;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author luyi
 * redisson的属性配置
 */
@ConfigurationProperties(prefix = "customize.redission")
public class RedissionProperties {

    private RedissonMode mode;


    private List<RedissionHost> hosts;


    public List<RedissionHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<RedissionHost> hosts) {
        this.hosts = hosts;
    }
}
