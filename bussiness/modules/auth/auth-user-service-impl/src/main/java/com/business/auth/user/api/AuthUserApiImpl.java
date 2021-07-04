package com.business.auth.user.api;

import com.business.auth.user.component.UserLogin;
import com.business.auth.user.component.UserLoginHandler;
import com.business.auth.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
public class AuthUserApiImpl implements AuthUserApi{

    private UserLoginHandler authUserHandler;
    @Override
    public String login(String type, LoginVO loginVO) {
        return authUserHandler.login(type,loginVO);
    }

    @Autowired
    public void setAuthUserHandler(UserLoginHandler authUserHandler) {
        this.authUserHandler = authUserHandler;
    }
}
