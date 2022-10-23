package io.github.architers.common.jwttoken;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class TenantInfo {
    /**
     * 租户编码
     */
    private String code;
    /**
     * 租户ID
     */
    private Integer id;

    /**
     * token最大时间
     */
    private Integer tokenMaxTime;

    /**
     * 是否自动刷新token
     */
    private boolean refreshToken;
}
