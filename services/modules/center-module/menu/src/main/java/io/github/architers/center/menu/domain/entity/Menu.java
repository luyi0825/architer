package io.github.architers.center.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单
 *
 * @author luyi
 */
@Data
@TableName("sys_menu")
public class Menu extends BaseTenantEntity implements Serializable {

    private Long id;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuCaption;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 父级ID
     */
    private String parentCode;

    /**
     * 备注
     */
    private String remark;


}
