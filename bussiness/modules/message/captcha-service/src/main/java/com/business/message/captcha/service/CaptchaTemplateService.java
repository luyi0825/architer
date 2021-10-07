package com.business.message.captcha.service;


import com.architecture.mybatisplus.service.BaseService;
import com.business.message.captcha.entity.CaptchaTemplate;


/**
 * 验证码配置
 *
 * @author luyi
 * @date 2021-06-20 00:41:42
 */
public interface CaptchaTemplateService extends BaseService<CaptchaTemplate> {
    /**
     * 添加验证码配置
     *
     * @param captchaTemplate 需要添加的验证码模板信息
     */
    void addCaptchaTemplate(CaptchaTemplate captchaTemplate);

    /**
     * 通过主键id查询
     *
     * @param id 主键ID
     * @return 验证码模板
     */
    CaptchaTemplate findById(Integer id);

    /**
     * 更新
     *
     * @param captchaTemplate 更新的信息
     */
    void updateCaptchaTemplate(CaptchaTemplate captchaTemplate);

    /**
     * 通过验证码编码获取
     *
     * @param code 验证码编码
     * @return 验证码配置信息
     */
    CaptchaTemplate findByCode(String code);
}

