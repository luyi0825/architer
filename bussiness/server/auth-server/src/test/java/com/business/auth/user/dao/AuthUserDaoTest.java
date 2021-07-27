package com.business.auth.user.dao;


import com.business.auth.user.entity.AuthUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * 权限用户测试类
 *
 * @author luyi
 */
@SpringBootTest
@RunWith(value = SpringRunner.class)
public class AuthUserDaoTest {

    private AuthUserDao authUserDao;

    @Test
    public void insert() {
        AuthUser user = new AuthUser();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());
        authUserDao.insert(user);
    }

    @Autowired
    public void setAuthUserDao(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }
}
