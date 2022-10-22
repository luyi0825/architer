package io.github.architers.common.jwttoken;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author luyi
 * 用户信息
 */
@Data
public class UserInfo {

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
     * 角色信息
     */
    private List<RoleInfo> roles;

    /**
     * 数据权限
     */
    private Set<String> permissions;


    @Data
    public static
    class RoleInfo {
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


}
