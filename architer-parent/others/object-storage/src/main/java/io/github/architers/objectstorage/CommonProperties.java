package io.github.architers.objectstorage;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDefaultBucket() {
        return defaultBucket;
    }

    public void setDefaultBucket(String defaultBucket) {
        this.defaultBucket = defaultBucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
