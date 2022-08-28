package com.test.email;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mail配置属性
 * <li>支持多邮件服务类型配置</li>
 * <li>支持一个邮件服务类型配置多个发送用户</li>
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architecture.mail")
@Data
public class MailProperties {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 邮箱服务配置信息
     */
    private Map<String, MailServer> servers;
    /**
     * Session JNDI name. When set, takes precedence over other Session settings.
     */
    private String jndiName;

    /**
     * 是否测试连接
     */
    private boolean testConnection = true;

    @Data
    static class MailServer {
        /**
         * SMTP server host. For instance, 'smtp.example.com'.
         */
        private String host;

        /**
         * SMTP server port.
         */
        private Integer port;

        /**
         * 邮件用户信息，支持配置多个
         */
        private Set<MailUser> mailUsers;

        /**
         * Protocol used by the SMTP server.
         */
        private String protocol = "smtp";

        /**
         * Default MimeMessage encoding.
         */
        private Charset defaultEncoding = DEFAULT_CHARSET;

        /**
         * Additional JavaMail Session properties.
         */
        private Map<String, String> properties = new HashMap<>();


    }


    /**
     * 邮件用户
     *
     * @author luyi
     */
    @Data
    public static class MailUser {

        /**
         * Login user of the SMTP server.
         */
        private String username;

        /**
         * Login password of the SMTP server.
         */
        private String password;
    }


}
