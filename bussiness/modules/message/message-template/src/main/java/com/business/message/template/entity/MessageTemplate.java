package com.business.message.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.architecture.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * 描述：消息模板
 *
 * @author luyi
 * @date 2021/1/22
 */
@TableName("t_message_template")
@Data
@ToString
public class MessageTemplate extends BaseEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageTemplate that = (MessageTemplate) o;
        return Objects.equals(templateName, that.templateName) && Objects.equals(templateCode, that.templateCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateName, templateCode);
    }
}
