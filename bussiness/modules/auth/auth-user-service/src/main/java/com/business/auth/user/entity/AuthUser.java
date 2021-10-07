package com.business.auth.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luyi
 * 权限用户
 */
@Data
@TableName(value = "t_auth_user")
public class AuthUser implements Serializable {
    /**
     * 用户主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 最后登录ID
     */
    private String lastLoginIp;
    /**
     * 登录错误次数
     */
    private String errorCount;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
}
