package com.business.message.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述：消息模板
 *
 * @author luyi
 * @date 2021/1/22
 */
@TableName("t_message_template")
@Data
public class MessageTemplate {
    /**
     * 描述：模板名称
     */
    @TableField("template_name")
    private String templateName;
    /**
     * 模板编码
     */
    @TableField("template_code")
    @TableId
    private String templateCode;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 主题
     */
    private String subject;

    /**
     * 消息过期时间：分钟
     */
    @TableField("expire_time")
    private Long expireTime;


}
