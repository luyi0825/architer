package io.github.architers.webhook;

import io.github.architers.webhook.service.IWebHookService;
import io.github.architers.webhook.service.IWebhookLimit;
import io.github.architers.webhook.service.impl.RedissonWebhookLimit;
import io.github.architers.webhook.service.impl.WebHookServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Webhook自动配置类
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(WebhookProperties.class)
public class WebhookAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IWebhookLimit webhookLimit(WebhookProperties webhookProperties) {
        return new RedissonWebhookLimit(webhookProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public IWebHookService webHookService(){
        return new WebHookServiceImpl();
    }
}
