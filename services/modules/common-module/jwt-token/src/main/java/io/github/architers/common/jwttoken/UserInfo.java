package io.github.architers.common.jwttoken;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author luyi
 * 用户信息
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 用户名称
     */
    private String userCaption;

    /**
     * 租户信息
     */
    public TenantInfo tenantInfo;

    /**
     * 角色信息
     */
    private List<RoleInfo> roles;

    /**
     * 数据权限
     */
    private Set<String> permissions;


}
