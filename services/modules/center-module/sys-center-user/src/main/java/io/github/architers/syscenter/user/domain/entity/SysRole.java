package io.github.architers.syscenter.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import io.github.architers.context.valid.ListValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class SysRole extends BaseTenantEntity implements Serializable {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色英文名称
     */
    @NotBlank(message = "角色英文名称")
    private String roleName;
    /**
     * 角色中文名称
     */
    @NotBlank(message = "角色中文名称")
    private String roleCaption;

    /**
     * 备注
     */
    @Size(message = "备注信息最多50字符", max = 50)
    private String remark;

    /**
     * 状态:0禁用，1启用
     */
    @ListValue(value = {"0", "1"}, message = "状态有误")
    private Byte status;

}
