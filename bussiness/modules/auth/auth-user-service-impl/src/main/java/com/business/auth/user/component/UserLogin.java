package com.business.auth.user.component;

import com.business.auth.user.entity.AuthUser;

/**
 * @author luyi
 */
public interface UserLogin {
    /**
     * 是否匹配，匹配则执行对应的方法
     *
     * @param loginType 登录类型
     * @return 是否满足
     */
    boolean match(String loginType);

    /**
     * 寻找用户
     *
     * @param userId 标识
     * @return 授权用户信息
     */
    AuthUser findAuthUser(String userId);
}
