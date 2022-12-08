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
@ConfigurationProperties(prefix = "spring.cloud.architer.load-balance")

@Data
public class WebhookProperties {

    /**
     * 默认限流
     */
    private Limit defaultLimit = new Limit();

    /**
     * 机器人配置
     */
    private Map<String, RobotConfig> robotConfigs;


    @Data

    static
    class Limit {

        /**
         * 限流数量
         */
        private int num = 20;
        /**
         * 限流时间(-1表示不限流)
         */
        private int time = 1;
        /**
         * 单位
         */
        private TimeUnit unit = TimeUnit.MINUTES;


    }

    @Data
    class RobotConfig {
        /**
         * 限流
         */
        private Limit limit;
    }



}
