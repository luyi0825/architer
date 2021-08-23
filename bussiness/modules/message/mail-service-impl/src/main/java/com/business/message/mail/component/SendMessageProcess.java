package com.business.message.mail.component;



import com.architecture.context.cache.CacheService;
import com.architecture.context.exception.ServiceException;
import com.business.message.mail.entity.MessageLimit;
import com.business.message.mail.entity.MessageTemplate;
import com.business.message.mail.model.Email;
import com.business.message.mail.service.MessageLimitService;
import com.business.message.mail.service.MessageTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 发送消息处理
 */
@Component
public class SendMessageProcess {


    private MessageTemplateService messageTemplateService;


    private MessageLimitService messageLimitService;


    private List<SendMessageAdapter> sendMessageAdapters;

    private CacheService cacheService;


    public void send(Email email) {
        if (StringUtils.isBlank(email.getReceivers())) {
            throw new ServiceException("接收人不能为空");
        }
        String templateCode = email.getTemplateCode();
        if (templateCode == null) {
            throw new ServiceException("编码不能为空");
        }
        //得到消息模板
        MessageTemplate messageTemplate = messageTemplateService.getMessageTemplateByCode(templateCode);
        if (messageTemplate == null) {
            throw new ServiceException("消息模板不存在");
        }
        //得到消息限制
        MessageLimit messageLimit = messageLimitService.getMessageLimitByTemplateCode(templateCode);

        for (SendMessageAdapter sendMessageAdapter : sendMessageAdapters) {
            if (sendMessageAdapter.support(templateCode)) {
                //发送前置处理
                sendBeforeProcess(email, messageLimit);
                //发送消息
                boolean bool = sendMessageAdapter.sendMessage(email, messageTemplate, messageLimit);
                if (!bool) {
                    throw new ServiceException("发送失败");
                }
                //发送后置处理
                sendAfterProcess(email, messageLimit);
            }
        }
        throw new ServiceException("没有找到对应的模板消息！");
    }

    /**
     * 发送之前处理的流程：判断是否已经被限制
     *
     * @param email        发送的邮件信息
     * @param messageLimit 邮箱限制信息
     */
    private void sendBeforeProcess(Email email, MessageLimit messageLimit) {
        if (messageLimit == null) {
            return;
        }
        String ip = email.getIp();
        //说明有限制的,防止接口被重刷
        if (isIntervals(messageLimit)) {
            //将前缀放入常量池
            String intervalsKey = (email.getTemplateCode() + "intervals::").intern() + ip;
            if (cacheService.get(intervalsKey) != null) {
                throw new ServiceException("请稍后再试!");
            }
        }
    }

    /**
     * @param messageLimit 消息限制
     * @return 是否时间限制
     */
    private boolean isIntervals(MessageLimit messageLimit) {
        if (messageLimit == null) {
            return false;
        }
        return messageLimit.getIntervals() > 0;
    }


    /**
     * 消息发送后的处理逻辑
     *
     * @param email        邮件信息
     * @param messageLimit 消息限制
     */
    private void sendAfterProcess(Email email, MessageLimit messageLimit) {
        if (isIntervals(messageLimit)) {
            String intervalsKey = (email.getTemplateCode() + "intervals::").intern() + email.getIp();
            cacheService.set(intervalsKey, "1", messageLimit.getIntervals(), TimeUnit.SECONDS);
        }
    }

    @Autowired
    public void setMessageTemplateService(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    @Autowired
    public void setMessageLimitService(MessageLimitService messageLimitService) {
        this.messageLimitService = messageLimitService;
    }

    @Autowired
    public void setSendMessageAdapters(List<SendMessageAdapter> sendMessageAdapters) {
        this.sendMessageAdapters = sendMessageAdapters;
    }

    @Autowired
    public SendMessageProcess setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
        return this;
    }
}
