package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;

public interface SysTenantService {
    SysTenant findById(Integer tenantId);

    /**
     * 判断租户是否有效的
     * @param tenantId 租户ID
     */
    void isValid(Integer tenantId);

}
