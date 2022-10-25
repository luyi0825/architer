package io.github.architers.syscenter.user.domain.entity;

import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

@Data
public class SysMenuList extends BaseTenantEntity {

    /**
     * 列表ID
     */
    private Long id;

    /**
     * 列表编码
     */
    private String listCode;

    /**
     * 列表名称
     */
    private String listCaption;

    /**
     * 备注信息
     */
    private String remark;



}
