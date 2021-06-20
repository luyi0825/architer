package com.business.message.mail.component;


import com.business.message.mail.entity.MessageLimit;
import com.business.message.mail.entity.MessageTemplate;
import com.business.message.mail.model.Email;

/**
 * 消息发送适配器
 *
 * @author luyi
 */
public interface SendMessageAdapter {
    /**
     * 是否满足适配
     *
     * @param messageCode 消息代码
     * @return 是否支持
     */
    boolean support(String messageCode);

    /**
     * 发送消息
     *
     * @param messageTemplate 消息模板
     * @param email           邮件信息
     * @param messageLimit    消息限制
     * @return 是否发送成功
     */
    boolean sendMessage(Email email, MessageTemplate messageTemplate, MessageLimit messageLimit);


}
