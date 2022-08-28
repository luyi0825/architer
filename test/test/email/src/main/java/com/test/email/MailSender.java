package com.test.email;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

/**
 * 邮件发送器
 *
 * @author luyi
 */
public interface MailSender extends JavaMailSender {

    /**
     * 得到监控状态
     *
     * @return 该发送器的健康状态
     */
    Integer getHealthyStatus();

    /**
     * 测试连接
     *
     * @throws MessagingException 测试失败抛出的异常
     */
    void testConnection() throws MessagingException;

    /**
     * 得到分组
     *
     * @return 分组标识
     */
    String getGroup();

    /**
     * 得到用户名
     */
    String getUsername();


}
