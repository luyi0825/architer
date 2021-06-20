package com.business.message.captcha.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 验证码配置
 *
 * @author luyi
 */
@Data
@TableName("t_message_captcha_config")
public class CaptchaConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Integer id;
    /**
     * 验证码长度
     */
    private Integer captchaLength;
    /**
     * 验证码类型
     */
    private Integer captchaType;
    /**
     * 过期时间/单位分钟
     */
    private Integer expireTime;
    /**
     * 添加人
     */
    private String addUser;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 更新人
     */
    private Date updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;

}
