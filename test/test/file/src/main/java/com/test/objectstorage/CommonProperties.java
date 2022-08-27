package com.test.objectstorage;

import lombok.Data;

/**
 * 公共的属性配置
 *
 * @author luyi
 */
@Data
public class CommonProperties {
    /**
     * 是否启用(默认是)
     */
    protected boolean enabled = true;

    /**
     * 端点
     */
    protected String endpoint;
    /**
     * 账户
     */
    protected String accessKey;

    /**
     * 秘钥（密码）
     */
    protected String secretKey;

    /**
     * 默认的令牌桶
     */
    protected String defaultBucket;
    /**
     * 地区
     */
    protected String region;
}
