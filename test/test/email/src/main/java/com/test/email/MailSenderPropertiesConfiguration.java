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
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Auto-configure a {@link MailSender} based on properties configuration.
 *
 * @author Oliver Gierke
 * @author Eddú Meléndez
 * @author Stephane Nicoll
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
class MailSenderPropertiesConfiguration implements BeanFactoryPostProcessor {


    private ApplicationContext applicationContext;

    private MailProperties mailProperties;

    public MailSenderPropertiesConfiguration(com.test.email.MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }


    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

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
                //注册单例bean
                configurableListableBeanFactory.registerSingleton(beanName, sender);
            }
        });

    }

    private String getRegisterBeanName(String group, int num) {
        return group + "_mailSend" + num;
    }


}
