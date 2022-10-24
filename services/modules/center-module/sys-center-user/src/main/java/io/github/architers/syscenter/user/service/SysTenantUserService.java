package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.entity.SysTenantUser;

public interface SysTenantUserService {
    void insertOne(SysTenantUser sysTenantUser);

    long countByUserId(Long id);
}
