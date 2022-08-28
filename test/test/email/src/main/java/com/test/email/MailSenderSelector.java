package com.test.email;

/**
 * 邮件发送选择器
 *
 * @author luyi
 */

public interface MailSenderSelector {

    MailSender selectOne(String group);

    MailSender selectHealthyOne(String group);

     void register(MailSender mailSender);
}
