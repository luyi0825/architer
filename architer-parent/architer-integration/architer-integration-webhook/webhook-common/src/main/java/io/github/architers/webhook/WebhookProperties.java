package io.github.architers.webhook;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Webhook属性配置
 * <p>
 * 默认一分钟限流20条
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architers.message.webhook")
@Data
public class WebhookProperties {

    /**
     * 默认限流
     */
    private Limit defaultLimit = new Limit();

    /**
     * 限流key前缀
     */
    private String limitKeyPrefix = "webhook:limit:";

    /**
     * 机器人配置
     */
    private Map<String, RobotConfig> robotConfigs;


    @Data
    public static class RobotConfig {
        /**
         * 限流
         */
        private Limit limit;

        /**
         * 机器人key
         */
        private String key;

        /**
         * 秘钥
         */
        private String secret;

        /**
         * 机器人类型
         */
        private String robotType;
    }

    @Data
    public static class Limit {

        /**
         * 限流数量
         */
        private int num = 20;
        /**
         * 限流时间(-1表示不限流
         * <li>单位秒</li>
         */
        private Long time = 60L;


    }


}
