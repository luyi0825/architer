package io.github.architers.syscenter.user.domain.entity;

import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

/**
 * 租户用户
 *
 * @author luyi
 */
@Data
public class SysTenantUser extends BaseTenantEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;


}
