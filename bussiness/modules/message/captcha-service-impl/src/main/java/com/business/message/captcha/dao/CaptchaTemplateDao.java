package com.business.message.captcha.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.message.captcha.entity.CaptchaTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码模板
 *
 * @author luyi
 * @date 2021-06-20 00:41:42
 */
@Mapper
public interface CaptchaTemplateDao extends BaseMapper<CaptchaTemplate> {

}
