package com.architecture.ultimate.starter.web.validexception;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


public class User implements Serializable {
    @Length(min = 5, max = 20, message = "用户名在5~10位")
    private String username;
    @Length(min = 5, max = 20, message = "password在5~10位")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
