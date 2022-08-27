package com.test.file;

import lombok.Data;

/**
 * 公共的属性配置
 *
 * @author luyi
 */
@Data
public class CommonProperties {
    /**
     * 是否启用(是)
     */
    private boolean enabled = true;

    /**
     * 端点
     */
    private String endpoint;
    /**
     * 账户
     */
    private String accessKey;

    /**
     * 秘钥（密码）
     */
    private String secretKey;

    /**
     * 默认的令牌桶
     */
    private String defaultBucket;
}
