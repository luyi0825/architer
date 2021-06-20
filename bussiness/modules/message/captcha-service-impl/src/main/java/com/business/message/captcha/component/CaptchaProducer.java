package com.business.message.captcha.component;

import com.business.message.captcha.CaptchaType;
import com.business.message.captcha.entity.CaptchaConfig;
import com.business.message.captcha.service.CaptchaConfigService;
import com.core.cache.redis.RedisConstants;
import com.core.cache.redis.StringRedisService;
import com.core.captcha.CharacterGifCaptcha;
import com.core.captcha.CharacterStaticCaptcha;
import com.core.captcha.base.Captcha;
import com.core.captcha.base.CharacterCaptcha;
import com.core.captcha.utils.CharacterCaptchaUtil;
import com.core.module.common.exception.ServiceException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证码生成器
 *
 * @author luyi
 */
@Component
public class CaptchaProducer {
    private final CaptchaConfigService captchaConfigService;
    private final StringRedisService redisService;
    private final static String SESSION_CAPTCHA = "session_captcha";

    public CaptchaProducer(CaptchaConfigService captchaConfigService,
                           StringRedisService redisService) {
        this.captchaConfigService = captchaConfigService;
        this.redisService = redisService;
    }

    /**
     *
     */
    public String produceImage(HttpServletResponse response, HttpSession session, String captchaCode) {
        CaptchaConfig captchaConfig = captchaConfigService.findByCode(captchaCode);
        if (captchaConfig == null) {
            throw new ServiceException("验证码配置不存在");
        }
        String type = captchaConfig.getCaptchaType();
        Captcha captcha = null;
        if (CaptchaType.PICTURE_PNG.getType().equals(type)) {
            captcha = new CharacterStaticCaptcha();
        } else if (CaptchaType.PICTURE_GIF.getType().equals(type)) {
            captcha = new CharacterGifCaptcha();
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            String code = captcha.out(response.getOutputStream(), null);
            String key = SESSION_CAPTCHA + RedisConstants.SPLIT + session.getId();
            redisService.set(key, code, captchaConfig.getExpireTime() * 60);
            return code;
        } catch (Exception e) {
            throw new ServiceException("生成验证码失败", e);
        }
    }

    public void produceMail(String captchaCode, String mail) {
        CaptchaConfig captchaConfig = captchaConfigService.findByCode(captchaCode);
        if (captchaConfig == null) {
            throw new ServiceException("验证码配置不存在");
        }
        String code = CharacterCaptchaUtil.getCode(captchaConfig.getCaptchaLength());
    }
}
