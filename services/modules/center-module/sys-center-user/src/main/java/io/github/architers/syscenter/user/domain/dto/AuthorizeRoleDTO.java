package io.github.architers.syscenter.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 授权用户角色
 *
 * @author luyi
 */
@Data
public class AuthorizeRoleDTO {


    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 添加的角色ID
     */
    private List<Long> addRoleIds;

    /**
     * 删除的用户角色ID
     */
    private List<Long> deleteIds;


}
