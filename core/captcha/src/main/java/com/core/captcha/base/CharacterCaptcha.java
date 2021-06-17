package com.core.captcha.base;

/**
 * @author luyi
 * 字符验证码
 */
public abstract class CharacterCaptcha extends Captcha {
    /**
     * 定义验证码字符.去除了0、O、I、L等容易混淆的字母
     */
    public static final char[] CHARS = {'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final int CHARS_LENGTH = CHARS.length;


    @Override
    protected String generateCode(int len) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = RANDOM.nextInt(CHARS_LENGTH);
            stringBuffer.append(CHARS[index]);
        }
        return stringBuffer.toString();
    }

}
