package com.business.message.captcha.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.message.captcha.entity.CaptchaConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码配置
 * 
 * @author luyi
 * @email ${email}
 * @date 2021-06-20 00:41:42
 */
@Mapper
public interface CatpchaConfigDao extends BaseMapper<CaptchaConfig> {
	
}
