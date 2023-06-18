package io.github.architers.webhook.wechat;

import io.github.architers.webhook.WebhookAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
@ImportAutoConfiguration(classes = WebhookAutoConfiguration.class)
@EnableFeignClients("io.github.architers.webhook.wechat.remote")
public class WechatWebHookAutoConfiguration {
}
