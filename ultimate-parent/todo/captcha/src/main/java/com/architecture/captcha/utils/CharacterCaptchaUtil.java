package com.architecture.captcha.utils;

import com.architecture.captcha.CaptchaConstants;

/**
 * 字符验证码工具类
 * @author luyi
 */
public class CharacterCaptchaUtil {
    /**
     * 定义验证码字符.去除了0、O、I、L等容易混淆的字母
     */
    private static final char[] CHARS = {'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final int CHARS_LENGTH = CHARS.length;

    public static String getCode(int length){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index =  CaptchaConstants.RANDOM.nextInt(CHARS_LENGTH);
            stringBuffer.append(CHARS[index]);
        }
        return stringBuffer.toString();
    }


}
