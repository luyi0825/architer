package com.lz.thread.properties;


import com.lz.thread.RejectedHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 自定义公共线程池配置属性
 *
 * @author luyi
 */
@Data
@ConfigurationProperties(prefix = "customize.thread-pool", ignoreInvalidFields = true)
public class TaskExecutorProperties {
    private Map<String, ThreadPoolConfig> configs;


}
