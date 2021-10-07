package com.business.message.captcha.entity;

import com.architecture.context.valid.ListValue;
import com.architecture.context.valid.group.AddGroup;
import com.architecture.context.valid.group.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
    @NotNull(message = "验证码长度不能为空")
    @Min(value = 4, message = "验证码最小长度是4", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 50, message = "验证码最大长度为50")
    private Integer captchaLength;
    /**
     * 验证码类型
     */
    @ListValue(value = {"1", "2", "10", "20"}, message = "验证码类型有误")
    private String captchaType;
    /**
     * 验证码编码
     */
    private String captchaCode;
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
    private Date addTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;

}
