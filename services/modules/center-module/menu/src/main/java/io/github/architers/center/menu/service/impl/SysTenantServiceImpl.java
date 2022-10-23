package io.github.architers.center.menu.service.impl;

import io.github.architers.center.menu.dao.SysTenantDao;
import io.github.architers.center.menu.domain.entity.SysTenant;
import io.github.architers.center.menu.service.SysTenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luyi
 */
@Service
public class SysTenantServiceImpl implements SysTenantService {

    @Resource
    private SysTenantDao sysTenantDao;

    public SysTenant findById(Integer tenantId) {
        return sysTenantDao.selectById(tenantId);
    }
}
