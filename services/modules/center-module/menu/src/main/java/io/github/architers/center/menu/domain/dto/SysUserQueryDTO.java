package io.github.architers.center.menu.domain.dto;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class SysUserQueryDTO {

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 用户中文名称
     */
    private String userCaption;

    /**
     * 状态:0禁用，1启用
     */
    private Byte status;

    /**
     * 租户ID
     */
    private Integer tenantId;


}
