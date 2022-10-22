package io.github.architers.center.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 菜单
 *
 * @author luyi
 */
@Data
@TableName("sys_menu")
public class Menu extends BaseTenantEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单编码
     */
    @NotEmpty(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "菜单名称不能为空")
    private String menuCaption;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单类型
     */
    @NotNull(message = "菜单名称不能为空")
    private Byte menuType;

    /**
     * 父级ID
     */
    private String parentCode;

    /**
     * 状态:0禁用，1启用
     */
    private Byte status;

    /**
     * 备注
     */
    @Size(max = 50, message = "菜单备注超过50字符")
    private String remark;


}
