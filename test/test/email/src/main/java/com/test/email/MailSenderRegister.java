/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.email;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


class MailSenderRegister implements ApplicationContextAware {


    //  @Resource
    private MailProperties mailProperties;

    private MailSenderSelector mailSenderSelector;

    public MailSenderRegister(MailProperties mailProperties, MailSenderSelector mailSenderSelector) {
        this.mailProperties = mailProperties;
        this.mailSenderSelector = mailSenderSelector;
    }


    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }


    private String getRegisterBeanName(String group, int num) {
        return group + "_mailSend" + num;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        this.registerSingletonMailSender(context.getBeanFactory());
    }


    /**
     * 注册单例邮件发送器
     */
    public void registerSingletonMailSender(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        Map<String, MailProperties.MailServer> servers = mailProperties.getServers();
        if (CollectionUtils.isEmpty(servers)) {
            throw new IllegalArgumentException("mail servers is empty!");
        }
        servers.forEach((group, mailServer) -> {
            Set<MailProperties.MailUser> mailUsers = mailServer.getMailUsers();
            if (CollectionUtils.isEmpty(mailUsers)) {
                throw new IllegalArgumentException("group mailUsers is empty:" + group);
            }
            int num = 0;
            for (MailProperties.MailUser mailUser : mailUsers) {
                DefaultMailSendImpl sender = new DefaultMailSendImpl();
                sender.setGroup(group);
                sender.setHost(mailServer.getHost());
                if (mailServer.getPort() != null) {
                    sender.setPort(mailServer.getPort());
                }
                sender.setUsername(mailUser.getUsername());
                sender.setPassword(mailUser.getPassword());
                sender.setProtocol(mailServer.getProtocol());
                if (mailServer.getDefaultEncoding() != null) {
                    sender.setDefaultEncoding(mailServer.getDefaultEncoding().name());
                }
                if (!mailServer.getProperties().isEmpty()) {
                    sender.setJavaMailProperties(asProperties(mailServer.getProperties()));
                }
                String beanName = this.getRegisterBeanName(group, ++num);
                validateConnection(sender);
                //注册单例bean
                configurableListableBeanFactory.registerSingleton(beanName, sender);
                mailSenderSelector.register(sender);
            }
        });

    }

    public void validateConnection(MailSender mailSender) {
        try {
            mailSender.testConnection();
        } catch (MessagingException ex) {
          //  throw new IllegalStateException("Mail server is not available", ex);
        }
    }
}
