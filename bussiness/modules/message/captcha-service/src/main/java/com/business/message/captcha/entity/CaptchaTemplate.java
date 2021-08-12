package com.business.message.captcha.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

import com.architecture.ultimate.mybatisplus.entity.BaseEntity;
import lombok.Data;

/**
 * 验证码模板
 *
 * @author luyi
 */
@Data
@TableName("t_message_captcha_template")
public class CaptchaTemplate extends BaseEntity implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private Integer id;
    /**
     * 编码
     */
    private String templateCode;
    /**
     * 模板名称
     */
    private String templateName;
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
     * 验证码内容
     */
    private String content;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaptchaTemplate that = (CaptchaTemplate) o;
        return Objects.equals(id, that.id) && Objects.equals(templateCode, that.templateCode) && Objects.equals(templateName, that.templateName) && Objects.equals(captchaLength, that.captchaLength) && Objects.equals(captchaType, that.captchaType) && Objects.equals(expireTime, that.expireTime) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateCode, templateName, captchaLength, captchaType, expireTime, content);
    }
}
