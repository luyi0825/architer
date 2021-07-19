package com.business.message.captcha.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.message.captcha.dao.CaptchaTemplateDao;
import com.business.message.captcha.entity.CaptchaTemplate;
import com.business.message.captcha.service.CaptchaTemplateService;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 验证码对应的service
 *
 * @author luyi
 */
@Service
public class CaptchaTemplateServiceImpl extends BaseServiceImpl<CaptchaTemplate> implements CaptchaTemplateService {

    private final CaptchaTemplateDao captchaTemplateDao;

    @Autowired
    public CaptchaTemplateServiceImpl(CaptchaTemplateDao captchaTemplateDao) {
        this.captchaTemplateDao = captchaTemplateDao;
    }

    @Override
    public void addCaptchaTemplate(CaptchaTemplate captchaTemplate) {
        captchaTemplate.setAddTime(new Date());
        captchaTemplateDao.insert(captchaTemplate);
    }

    @Override
    public CaptchaTemplate findById(Integer id) {
        return captchaTemplateDao.selectById(id);
    }

    @Override
    public void updateCaptchaTemplate(CaptchaTemplate captchaTemplate) {
        captchaTemplateDao.updateById(captchaTemplate);
    }

    @Override
    public CaptchaTemplate findByCode(String captchaCode) {
        QueryWrapper<CaptchaTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("captcha_code", captchaCode);
        return captchaTemplateDao.selectOne(queryWrapper);
    }
}