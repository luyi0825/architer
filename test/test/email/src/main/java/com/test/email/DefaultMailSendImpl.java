package com.test.email;

import com.sun.mail.smtp.SMTPSendFailedException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luyi
 * 默认的邮件发送器
 */
public class DefaultMailSendImpl extends JavaMailSenderImpl implements MailSender {

    private AtomicInteger healthyStatus = new AtomicInteger(HealthStatus.healthy.getStatus());

    private String group;

    private AtomicInteger failCount = new AtomicInteger();


    @Override
    public Integer getHealthyStatus() {
        return healthyStatus.get();
    }

    @Override
    public String getGroup() {
        return group;
    }

    public DefaultMailSendImpl setGroup(String group) {
        this.group = group;
        return this;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        try {
            super.send(simpleMessage);
        } catch (Exception e) {
            sendFail(e);

            throw e;
        }

    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        try {
            super.send(simpleMessages);
        } catch (Exception e) {
            sendFail(e);
            throw e;

        }

    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        try {
            super.send(mimeMessage);
        } catch (Exception e) {
            sendFail(e);
            throw e;
        }
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {

        try {
            super.send(mimeMessages);
        } catch (Exception e) {
            sendFail(e);
            throw e;
        }
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        super.send(mimeMessagePreparator);
        try {
            super.send(mimeMessagePreparator);
        } catch (Exception e) {
            sendFail(e);
            throw e;
        }
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        try {
            super.send(mimeMessagePreparators);
        } catch (Exception e) {
            sendFail(e);
            throw e;
        }
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        try {
            super.doSend(mimeMessages, originalMessages);
        } catch (Exception e) {
            sendFail(e);
            throw e;
        }
    }

    private void sendFail(Exception mailException) {

        if(mailException instanceof MailAuthenticationException){
            MailAuthenticationException mailAuthenticationException= (MailAuthenticationException) mailException;
        }
      //  org.springframework.mail.MailAuthenticationException: Authentication failed; nested exception is javax.mail.AuthenticationFailedException: 535 Login Fail. Please enter your authorization code to login. More information in http://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256

      //  SMTPSendFailedException exception =(SMTPSendFailedException) mailException;
      //  System.out.println(exception.getReturnCode());
        //设置为半健康的状态
        healthyStatus.compareAndSet(HealthStatus.healthy.getStatus(), HealthStatus.half_healthy.getStatus());
    }
}
