package com.business.message.mail.service.impl;


import com.business.message.mail.MessageConstants;
import org.springframework.stereotype.Component;

/**
 * email消息发送适配器
 *
 * @author ly
 */
@Component
public class EmailMessageSendAdaptor {

    public boolean support(String messageType) {
        return true;
       // return MessageConstants.MESSAGE_TYPE_EMAIL.equals(messageType);
    }

    public void sendMessage(String message, String templateCode) {

    }

}
