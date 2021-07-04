package com.business.auth.user.service;

import com.business.auth.user.entity.AuthUser;
import com.core.mybatisplus.service.BaseService;

/**
 * @author luyi
 * 授权用户service接口层
 */
public interface AuthUserService extends BaseService<AuthUser> {
    /**
     * 通过用户名查询
     *
     * @param userName 用户名
     * @return 用户信息
     */
    AuthUser findByUserName(String userName);

}
