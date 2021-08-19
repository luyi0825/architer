package com.architecture.captcha.base;

import com.architecture.captcha.utils.CharacterCaptchaUtil;

/**
 * @author luyi
 * 字符验证码
 */
public abstract class CharacterCaptcha extends Captcha {


    @Override
    protected String generateCode(int len) {
        return CharacterCaptchaUtil.getCode(len);
    }

}
