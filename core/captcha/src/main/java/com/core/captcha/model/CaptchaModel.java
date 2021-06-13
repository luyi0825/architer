package com.core.captcha.model;

import lombok.Data;

import java.awt.*;

/**
 * @author luyi
 * 验证码模型
 */
@Data
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
     *字体
     */
    private Font font;
}
