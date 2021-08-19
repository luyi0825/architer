package com.business.message.mail.component;


import com.architecture.context.common.exception.ServiceException;
import com.business.message.mail.MessageConstants;
import com.business.message.mail.entity.MessageLimit;
import com.business.message.mail.entity.MessageTemplate;
import com.business.message.mail.model.Email;
import com.business.message.mail.sender.EmailSender;
import com.architecture.cache.redis.StringRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 发送验证码适配器实现类
 *
 * @author luyi
 */
@Component
public class SendVerificationCodeMessageAdapterImpl implements SendMessageAdapter {

    private StringRedisService redisService;

    private EmailSender emailSender;

    /**
     * 验证码的字符
     */
    private static final String[] VERIFICATION_CODE_CHARACTERS = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "e", "f", "g", "h", "i", "j"
    };

    @Override
    public boolean support(String messageCode) {
        return MessageConstants.EMAIL_VERIFICATION_CODE.equals(messageCode);
    }

    @Override
    public boolean sendMessage(Email email, MessageTemplate messageTemplate, MessageLimit messageLimit) {
        String templateCode = email.getTemplateCode();
        if (StringUtils.isBlank(templateCode)) {
            throw new ServiceException("模板编码不能为空！");
        }
        if (StringUtils.isEmpty(email.getReceivers())) {
            throw new ServiceException("接收人不能为空！");
        }
        if (MessageConstants.EMAIL_VERIFICATION_CODE.equals(templateCode)) {
            return sendMailVerificationCode(email, messageTemplate);
        }
        //不满足就发送失败
        return false;
    }


    /**
     * 发送邮件验证码
     *
     * @param email           邮件消息
     * @param messageTemplate 模板
     * @return 是否发送成功
     */
    private boolean sendMailVerificationCode(Email email, MessageTemplate messageTemplate) {
        email.setSubject(messageTemplate.getSubject());
        //设置接收人
        String verificationCode = generateVerificationCode(6);
        String content = MessageFormat.format(messageTemplate.getContent(), verificationCode);
        email.setContent(content);
        //发送邮件
        emailSender.sendSimpleMail(email);
        //生成key
        String codeKey = email.getTemplateCode() + "::" + email.getReceivers();
        //设置有效期
        redisService.set(codeKey, verificationCode, messageTemplate.getExpireTime() * 60L);
        return true;
    }


    /**
     * 生成验证码
     *
     * @param length 验证码的长度大小
     */
    private String generateVerificationCode(int length) {
        //验证码生成字符库的大小
        int size = VERIFICATION_CODE_CHARACTERS.length;
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            //随机生成一个下标，取值
            int index = (int) (Math.random() * size);
            verificationCode.append(VERIFICATION_CODE_CHARACTERS[index]);
        }
        return verificationCode.toString();
    }


    @Autowired(required = false)
    public void setRedisService(StringRedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
}
