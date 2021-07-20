package com.architecture.ultimate.captcha.model;



import java.awt.*;

/**
 * @author luyi
 * 验证码模型
 */
public class CaptchaModel {
    /**
     * 图片的宽
     */
    private int width;
    /**
     *图片的高
     */
    private int height;
    /**
     * 验证码长度
     */
    private int len;
    /**
     * 字体
     */
    private Font font;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
