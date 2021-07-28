package com.business.message.mail.sender;



import com.architecture.ultimate.module.common.exception.ServiceException;
import com.business.message.mail.model.Email;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


/**
 * 邮件发送器
 * 支持配置多个系统的邮件发送者：用于防止发送的邮件到上限
 *
 * @author ly
 */
@Component
public class EmailSender {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    /**
     * 发送简单的邮件消息
     *
     * @param mail 邮件信息
     */
    public void sendSimpleMail(Email mail) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom("893431435@qq.com");
        //邮件接收人
        message.setTo(mail.getReceivers().split(","));
        //邮件主题
        message.setSubject(mail.getSubject());
        // 邮件内容
        message.setText(mail.getContent());
        //发送邮件
        try {
            javaMailSender.send(message);
            System.out.println("send..");
        } catch (Exception e) {
            if (e instanceof MailSendException) {
                MailSendException mailSendException = (MailSendException) e;
                SendFailedException sendFailedException = (SendFailedException) mailSendException.getFailedMessages().get(message);
                if (sendFailedException.getNextException() instanceof SMTPAddressFailedException) {
                    throw new ServiceException("邮件地址不正确！");
                }
            }
            throw new ServiceException("发送失败", e);
        }

    }

    public void sendMail(Email mail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("893431435@qq.com");
        helper.setTo(mail.getReceivers().split(","));
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());
        helper.setSentDate(new Date());
        javaMailSender.send(message);
        System.out.println("带附件的邮件发送成功");
    }


}
