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
    PICTURE_PNG("1", "图片验证码)"),
    PICTURE_GIF("2", "GIF图片验证码"),
    /**
     *邮件验证码
     */
    MAIL("10","邮件验证码"),
    /**
     * 短信验证码
     */
    SMS("20","短信验证码");
    private String type;
    private String caption;

    CaptchaType(String type, String caption) {
        this.type = type;
        this.caption = caption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public static String[] getTypes(){
        return null;
    }

}
