package io.github.architers.syscenter.user.service;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;

public interface SysTenantService {
    SysTenant findById(Integer tenantId);

    /**
     * 判断租户是否有效的
     * @param tenantId 租户ID
     */
    void isValid(Integer tenantId);

    PageResult<SysTenant> getTenantsByPage(PageRequest<SysTenant> pageRequest);

    void addTenant(SysTenant sysTenant);

    void editTenant(SysTenant sysTenant);

    void deleteById(Integer id);
}
