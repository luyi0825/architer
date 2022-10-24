package io.github.architers.syscenter.user.service.impl;

import io.github.architers.syscenter.user.StatusEnum;
import io.github.architers.syscenter.user.dao.SysTenantDao;
import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;
import io.github.architers.syscenter.user.service.SysTenantService;
import io.github.architers.context.exception.BusException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luyi
 */
@Service
public class SysTenantServiceImpl implements SysTenantService {

    @Resource
    private SysTenantDao sysTenantDao;

    @Override
    public SysTenant findById(Integer tenantId) {
        return sysTenantDao.selectById(tenantId);
    }

    @Override
    public void isValid(Integer tenantId) {
        SysTenant sysTenant = sysTenantDao.selectById(tenantId);
        if (sysTenant == null) {
            throw new BusException("租户不存在");
        }
        if (StatusEnum.DISABLED.getStatus().equals(sysTenant.getStatus())) {
            throw new BusException("租户已经被禁用");
        }
    }


}
