package com.business.auth.user.component;

import com.business.auth.user.entity.AuthUser;
import com.business.auth.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author luyi
 * 用户登录处理器
 */
@Component
public class UserLoginHandler {


    private List<UserLogin> userLogins;

    public String login(String loginType, LoginVO loginVO){
        AuthUser authUser;
        for (UserLogin userLogin : userLogins) {
            if(userLogin.match(loginType)){
                authUser = userLogin.findAuthUser(loginVO.getUsername());
            }
        }
        return null;
    }

    @Autowired
    public void setUserLogins(List<UserLogin> userLogins) {
        this.userLogins = userLogins;
    }
}
