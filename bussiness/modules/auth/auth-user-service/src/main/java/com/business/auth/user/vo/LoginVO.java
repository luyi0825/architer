package com.business.auth.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 * 用户登录信息
 */
@Data
public class LoginVO implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;

}
