package com.test.email;

/**
 * 邮件发送选择器
 *luyi082125
 * @author luyi
 *///13CA1B8BA952F53FAB10BE70FE5D4502E1DE3775

public interface MailSenderSelector {

    MailSender selectOne(String group);

    MailSender selectHealthyOne(String group);

     void register(MailSender mailSender);
}
