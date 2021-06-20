package com.business.message.captcha.service;

import com.business.message.captcha.entity.CaptchaConfig;
import com.core.mybatisplus.service.BaseService;

/**
 * 验证码配置
 *
 * @author luyi
 * @date 2021-06-20 00:41:42
 */
public interface CaptchaConfigService extends BaseService<CaptchaConfig> {
    /**
     * 添加验证码配置
     */
    void addCaptchaConfig(CaptchaConfig captchaConfig);
}

