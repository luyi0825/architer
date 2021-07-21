package com.architecture.ultimate.thread.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 自定义公共线程池配置属性
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "customize.thread-pool", ignoreInvalidFields = true)
public class TaskExecutorProperties {
    /**
     * key为线程池的标识，value为线程池配置信息
     */
    private Map<String, ThreadPoolConfig> configs;

    public Map<String, ThreadPoolConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, ThreadPoolConfig> configs) {
        this.configs = configs;
    }
}
