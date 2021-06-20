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

    /**
     * 通过主键id查询
     *
     * @param id 主键ID
     * @return 验证码配置
     */
    CaptchaConfig findById(Integer id);

    /**
     * 更新
     *
     * @param captchaConfig 更新的信息
     */
    void updateCaptchaConfig(CaptchaConfig captchaConfig);

    /**
     * 通过验证码编码获取
     *
     * @param captchaCode 验证码编码
     * @return 验证码配置信息
     */
    CaptchaConfig findByCode(String captchaCode);
}

