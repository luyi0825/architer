package io.github.architers.context.cache.consistency.rocketmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("architers.cache.consistency.rocketmq")
@Data
public class CacheRocketMqProperties {

    /**
     * 是否开启rocketmq缓存一致性
     */
    private boolean enabled = false;
    /**
     * 消息发送的topic
     */
    private String topic = "test";

    /**
     * 生产组
     */
    private String productGroup;

    /**
     * 本地缓存消费者组
     */
    private String localConsumeGroup;

    /**
     * 远程消费者组
     */
    private String remoteConsumeGroup;
}
