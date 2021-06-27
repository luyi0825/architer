package com.business.message.captcha;

/**
 * 验证码类型
 *
 * @author luyi
 */
public enum CaptchaType {
    /**
     * 图片验证吗
     */
    PICTURE_PNG(1, "图片验证码)"),
    PICTURE_GIF(2, "GIF图片验证码"),
    /**
     * 邮件验证码
     */
    MAIL(10, "邮件验证码"),
    /**
     * 短信验证码
     */
    SMS(20, "短信验证码");
    private int type;
    private String caption;

    CaptchaType(int type, String caption) {
        this.type = type;
        this.caption = caption;
    }

    public int getType() {
        return type;
    }

    public CaptchaType setType(int type) {
        this.type = type;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public static String[] getTypes() {
        return null;
    }

}
