package com.test.email;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class DefaultMailSendImpl extends JavaMailSenderImpl implements MailSender {

    private String healthyStatus;

    private String group;


    @Override
    public HealthStatus healthStatus() {
        return null;
    }

    public String getHealthyStatus() {
        return healthyStatus;
    }

    public DefaultMailSendImpl setHealthyStatus(String healthyStatus) {
        this.healthyStatus = healthyStatus;
        return this;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public DefaultMailSendImpl setGroup(String group) {
        this.group = group;
        return this;
    }
}
