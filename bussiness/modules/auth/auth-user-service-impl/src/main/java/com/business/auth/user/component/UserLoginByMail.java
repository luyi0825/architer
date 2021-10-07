package com.business.auth.user.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.auth.user.LoginType;
import com.business.auth.user.entity.AuthUser;
import com.business.auth.user.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 * 通过邮箱登录
 */
@Component
public class UserLoginByMail implements UserLogin {

    private AuthUserService authUserService;

    @Override
    public boolean match(String loginType) {
        return LoginType.MAIL.getType().equals(loginType);
    }

    @Override
    public AuthUser findAuthUser(String userId) {
        QueryWrapper<AuthUser> queryMapper = new QueryWrapper<>();
        queryMapper.eq(LoginType.MAIL.getDbField(), userId);
        return authUserService.selectOne(queryMapper);
    }

    @Autowired
    public void setAuthUserService(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }
}
