package com.business.message.captcha.api.impl;



import com.business.message.captcha.api.CaptchaConfigApi;
import com.business.message.captcha.entity.CaptchaConfig;
import com.business.message.captcha.service.CaptchaConfigService;
import com.core.mybatisplus.Pagination;
import com.core.mybatisplus.QueryParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
public class CaptchaConfigApiImpl implements CaptchaConfigApi {

    private final CaptchaConfigService captchaConfigService;

    public CaptchaConfigApiImpl(CaptchaConfigService captchaConfigService) {
        this.captchaConfigService = captchaConfigService;
    }

    @Override
    public Pagination pageQuery(QueryParam<CaptchaConfig> queryParam) {
        return captchaConfigService.pageQuery(queryParam);
    }

    @Override
    public void addCaptchaConfig(CaptchaConfig captchaConfig) {
        captchaConfigService.addCaptchaConfig(captchaConfig);
    }

    @Override
    public CaptchaConfig findById(Integer id) {
       return captchaConfigService.findById(id);
    }

    @Override
    public void updateCaptchaConfig(CaptchaConfig captchaConfig) {
        captchaConfigService.updateCaptchaConfig(captchaConfig);
    }
}