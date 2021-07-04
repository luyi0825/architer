package com.business.auth.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luyi
 * 权限用户
 */
@Data
public class AuthUser implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String mail;
    private String lastLoginIp;
    private String errorCount;
    private Date lastLoginTime;
}
