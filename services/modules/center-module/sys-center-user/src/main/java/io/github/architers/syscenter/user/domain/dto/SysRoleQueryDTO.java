package io.github.architers.syscenter.user.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class SysRoleQueryDTO implements Serializable {

    /**
     * 角色英文名称
     */
    private String roleName;

    /**
     * 角色中文名称
     */
    private String roleCaption;

    /**
     * 状态
     */
    private Byte status;
}
