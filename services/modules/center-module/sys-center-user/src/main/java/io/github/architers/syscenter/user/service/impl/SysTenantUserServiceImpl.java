package io.github.architers.syscenter.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.syscenter.user.dao.SysTenantUserDao;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;
import io.github.architers.syscenter.user.service.SysTenantUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysTenantUserServiceImpl implements SysTenantUserService {

    @Resource
    private SysTenantUserDao sysTenantUserDao;

    @Override
    public void insertOne(SysTenantUser sysTenantUser) {
        sysTenantUserDao.insert(sysTenantUser);
    }

    @Override
    public long countByUserId(Long userId) {
        Wrapper<SysTenantUser> sysUserWrapper = Wrappers.lambdaQuery(SysTenantUser.class)
                .eq(SysTenantUser::getUserId,userId);
        return sysTenantUserDao.selectCount(sysUserWrapper);
    }
}
