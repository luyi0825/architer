package io.github.architers.center.menu.service;

import io.github.architers.center.menu.domain.entity.SysTenant;

public interface SysTenantService {
    SysTenant findById(Integer tenantId);
}
