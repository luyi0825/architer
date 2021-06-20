package com.business.message.captcha.service.impl;


import com.business.message.captcha.dao.CatpchaConfigDao;
import com.business.message.captcha.entity.CaptchaConfig;
import com.business.message.captcha.service.CaptchaConfigService;
import com.core.mybatisplus.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 验证码对应的service
 *
 * @author luyi
 */
@Service
public class CaptchaConfigServiceImpl extends BaseServiceImpl<CaptchaConfig> implements CaptchaConfigService {

    private final CatpchaConfigDao catpchaConfigDao;

    @Autowired
    public CaptchaConfigServiceImpl(CatpchaConfigDao catpchaConfigDao) {
        this.catpchaConfigDao = catpchaConfigDao;
    }

    @Override
    public void addCaptchaConfig(CaptchaConfig captchaConfig) {
        captchaConfig.setAddTime(new Date());
        catpchaConfigDao.insert(captchaConfig);
    }
}