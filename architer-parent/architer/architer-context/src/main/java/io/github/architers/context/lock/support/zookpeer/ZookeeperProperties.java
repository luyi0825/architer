package io.github.architers.context.lock.support.zookpeer;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.client.ZKClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zk属性配置
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
@ConfigurationProperties(prefix = ZookeeperProperties.prefix)
public class ZookeeperProperties {

    public static final String prefix = "architers.lock.zk";

    /**
     * 是否开启zk分布式
     */
    private boolean enabled = false;

    /**
     * 服务地址：多个用英文逗号隔开
     */
    private String serverLists = "127.0.0.1:2181";

    /**
     * 连接超时时间(毫秒)
     */
    private int connectionTimeoutMs = 10_000;

    /**
     * 会话超时时间(毫秒）
     */
    private int sessionTimeoutMs = 10_000;

    /**
     * 最大关闭等待时间
     */
    private int maxCloseWaitMs = 2_000;
    /**
     * 命名空间
     */
    private String namespace;

}
