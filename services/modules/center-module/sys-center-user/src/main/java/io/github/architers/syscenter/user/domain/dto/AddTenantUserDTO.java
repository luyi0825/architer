package io.github.architers.syscenter.user.domain.dto;

import io.github.architers.context.valid.ListValue;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 添加租户用户
 *
 * @author luyi
 */
@Data
public class AddTenantUserDTO implements Serializable {

    /**
     * 登录用户名
     */
    @NotEmpty(message = "登录用户名不能为空")
    @Size(min = 5, max = 45, message = "登录用户名在5-45字符之间")
    private String userName;
    /**
     * 用户名称
     */
    @Size(max = 50, message = "用户名称最大50个字符")
    @NotEmpty(message = "用户名称不能为空")
    private String userCaption;
    /**
     * 租户ID
     */
    @NotNull(message = "租户ID不能为空")
    private Integer tenantId;

    /**
     * 用户状态:0禁用，1启用
     */
    @NotNull(message = "用户状态不能为空")
    @ListValue(message = "状态值有误", value = {"0", "1"})
    private Byte status;

}
