package com.architecture.context.thread.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 客户端属性文件
 *
 * @author luyi
 * @date 2021/4/26
 */
@Data
@ConfigurationProperties(prefix = "customize.thread-pool.client", ignoreInvalidFields = true)
public class ClientProperties {
    /**
     * 服务端地址
     */
    private String server;


}
