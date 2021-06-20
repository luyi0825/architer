package com.business.message.captcha.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Override
    public CaptchaConfig findById(Integer id) {
        return catpchaConfigDao.selectById(id);
    }

    @Override
    public void updateCaptchaConfig(CaptchaConfig captchaConfig) {
        catpchaConfigDao.updateById(captchaConfig);
    }

    @Override
    public CaptchaConfig findByCode(String captchaCode) {
        QueryWrapper<CaptchaConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("captcha_code", captchaCode);
        return catpchaConfigDao.selectOne(queryWrapper);
    }
}