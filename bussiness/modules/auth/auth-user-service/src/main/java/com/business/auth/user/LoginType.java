package com.business.auth.user;

/**
 * 登录类型
 * @author luyi
 */
public enum LoginType {
    /**
    * 邮箱
     */
    MAIL("mail","mail"),
    /**
     * 账号，用户名
     */
    ACCOUNT("account","username"),
    /**
     * 手机号
     */
    PHONE("phone","phone");

    /**
     * 类型
     */
    private String type;

    /**
     * 数据库字段
     */
    private String dbField;

    LoginType(String type, String dbField) {
        this.type = type;
        this.dbField = dbField;
    }

    public String getDbField() {
        return dbField;
    }

    public void setDbField(String dbField) {
        this.dbField = dbField;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
