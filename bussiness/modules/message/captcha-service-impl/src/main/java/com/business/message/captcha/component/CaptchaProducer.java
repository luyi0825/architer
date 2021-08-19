package com.business.message.captcha.component;

import com.architecture.cache.redis.RedisConstants;
import com.architecture.cache.redis.StringRedisService;
import com.architecture.captcha.CharacterGifCaptcha;
import com.architecture.captcha.CharacterStaticCaptcha;
import com.architecture.captcha.base.Captcha;
import com.architecture.captcha.utils.CharacterCaptchaUtil;
import com.business.message.captcha.CaptchaType;
import com.business.message.captcha.entity.CaptchaTemplate;
import com.business.message.captcha.service.CaptchaTemplateService;

import com.architecture.context.common.exception.ServiceException;
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
    private final CaptchaTemplateService captchaTemplateService;
    private final StringRedisService redisService;
    private final static String SESSION_CAPTCHA = "session_captcha";

    public CaptchaProducer(CaptchaTemplateService captchaTemplateService,
                           StringRedisService redisService) {
        this.captchaTemplateService = captchaTemplateService;
        this.redisService = redisService;
    }

    /**
     *
     */
    public String produceImage(HttpServletResponse response, HttpSession session, String captchaCode) {
        CaptchaTemplate captchaEntity = captchaTemplateService.findByCode(captchaCode);
        if (captchaEntity == null) {
            throw new ServiceException("验证码配置不存在");
        }
        int type = captchaEntity.getCaptchaType();
        Captcha captcha = null;
        if (CaptchaType.PICTURE_PNG.getType() == type) {
            captcha = new CharacterStaticCaptcha();
        } else if (CaptchaType.PICTURE_GIF.getType() == type) {
            captcha = new CharacterGifCaptcha();
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            String code = captcha.out(response.getOutputStream(), null);
            String key = SESSION_CAPTCHA + RedisConstants.SPLIT + session.getId();
            redisService.set(key, code, captchaEntity.getExpireTime() * 60);
            return code;
        } catch (Exception e) {
            throw new ServiceException("生成验证码失败", e);
        }
    }

    public void produceMail(String captchaCode, String mail) {
        CaptchaTemplate captchaEntity = captchaTemplateService.findByCode(captchaCode);
        if (captchaEntity == null) {
            throw new ServiceException("验证码配置不存在");
        }
        String code = CharacterCaptchaUtil.getCode(captchaEntity.getCaptchaLength());
    }
}
