package io.github.architers.common.jwttoken;

import lombok.Data;

/**
 * 角色信息
 * @author luyi
 */
@Data
public class RoleInfo {
    private Long roleId;
    private String roleCode;
    private String roleCaption;

    public RoleInfo() {

    }

    public RoleInfo(Long roleId, String roleCode, String roleCaption) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleCaption = roleCaption;
    }
}
