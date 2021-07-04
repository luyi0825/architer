package com.business.auth.user.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.auth.user.LoginType;
import com.business.auth.user.entity.AuthUser;
import com.business.auth.user.service.AuthUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过用户名登录
 * @author luyi
 */
@Component
public class UserLoginByUserName implements UserLogin{
    @Autowired
    private AuthUserService authUserService;
    @Override
    public boolean match(String loginType) {
        return LoginType.ACCOUNT.getType().equals(loginType);
    }

    @Override
    public AuthUser findAuthUser(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        QueryWrapper<AuthUser> queryMapper = new QueryWrapper<>();
        queryMapper.eq(LoginType.ACCOUNT.getDbField(), userId);
        return this.authUserService.selectOne(queryMapper);
    }
}
