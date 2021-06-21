package com.core.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @author luyi
 * 实体的基类
 */
public abstract class BaseEntity {
    /**
     * 添加人
     */
    private String addUser;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getAddUser() {
        return addUser;
    }

    public BaseEntity setAddUser(String addUser) {
        this.addUser = addUser;
        return this;
    }

    public Date getAddTime() {
        return addTime;
    }

    public BaseEntity setAddTime(Date addTime) {
        this.addTime = addTime;
        return this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public BaseEntity setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BaseEntity setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
