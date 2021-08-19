package com.business.message.captcha.api.impl;


import com.architecture.mybatisplus.QueryParam;
import com.architecture.query.common.model.Pagination;
import com.business.message.captcha.api.CaptchaTemplateApi;
import com.business.message.captcha.entity.CaptchaTemplate;
import com.business.message.captcha.service.CaptchaTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/captchaTemplateApi")
public class CaptchaTemplateApiImpl implements CaptchaTemplateApi {

    private final CaptchaTemplateService captchaTemplateService;

    public CaptchaTemplateApiImpl(CaptchaTemplateService captchaTemplateService) {
        this.captchaTemplateService = captchaTemplateService;
    }

    @Override
    public Pagination pageQuery(QueryParam<CaptchaTemplate> queryParam) {
        return captchaTemplateService.pageQuery(queryParam);
    }

    @Override
    public void addCaptchaTemplate(CaptchaTemplate captchaEntity) {
        captchaTemplateService.addCaptchaTemplate(captchaEntity);
    }

    @Override
    public CaptchaTemplate findById(Integer id) {
        return captchaTemplateService.findById(id);
    }

    @Override
    public void updateCaptchaTemplate(CaptchaTemplate captchaTemplate) {
        captchaTemplateService.updateCaptchaTemplate(captchaTemplate);
    }

    @Override
    public void delete(int id) {
        captchaTemplateService.delete(id);
    }
}