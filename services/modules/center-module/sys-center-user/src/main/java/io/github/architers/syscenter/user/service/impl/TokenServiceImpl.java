package io.github.architers.syscenter.user.service.impl;

import io.github.architers.syscenter.user.StatusEnum;
import io.github.architers.syscenter.user.domain.dto.LoginParamDTO;
import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.service.SysTenantService;
import io.github.architers.syscenter.user.service.SysUserService;
import io.github.architers.syscenter.user.service.TokenService;
import io.github.architers.common.jwttoken.JwtTokenUtils;
import io.github.architers.common.jwttoken.TenantInfo;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.context.exception.NoStackBusException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Administrator
 */
@Service
public class TokenServiceImpl implements TokenService {


    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTenantService sysTenantService;

    @Override
    public String loginByUserName(LoginParamDTO loginParamDTO) {
        SysUser sysUser = sysUserService.selectByUserName(loginParamDTO.getUserName());
        if (sysUser == null) {
            throw new NoStackBusException("用户【" + loginParamDTO.getUserName() + "】不存在");
        }
        if (!StatusEnum.ENABLED.getStatus().equals(sysUser.getStatus())) {
            throw new NoStackBusException("用户【" + loginParamDTO.getUserName() + "】已经被禁用");
        }
        if (!sysUser.getPassword().equals(loginParamDTO.getPassword())) {
            throw new NoStackBusException("用户名和密码不匹配");
        }
        //校验通过，生成token
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(sysUser.getId());
        userInfo.setUserName(sysUser.getUserName());
        userInfo.setUserCaption(sysUser.getUserCaption());

        //查询租户信息
        SysTenant tenant = sysTenantService.findById(sysUser.getTenantId());
        if (tenant == null) {
            throw new NoStackBusException("租户信息不存在");
        }
        if (!StatusEnum.ENABLED.getStatus().equals(tenant.getStatus())) {
            throw new NoStackBusException("租户已经被禁用");
        }
        //TODO 填充角色和权限
        TenantInfo tenantInfo = new TenantInfo();
        tenantInfo.setCode(tenant.getTenantCode());
        tenantInfo.setId(tenantInfo.getId());
        tenantInfo.setRefreshToken(Boolean.TRUE.equals(tenant.getTokenAutoRefresh()));
        tenantInfo.setTokenMaxTime(tenant.getTokenMaxTime());
        userInfo.setTenantInfo(tenantInfo);
        return JwtTokenUtils.sign(userInfo, new Date(),
                tenant.getTokenExpireTime() * 60_1000);
    }
}
