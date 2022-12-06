package io.github.architers.webhook;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Webhook自动配置类
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(WebhookProperties.class)
@EnableFeignClients(basePackages = "io.github.architers.webhook.feign")
public class WebhookAutoConfiguration {
}
