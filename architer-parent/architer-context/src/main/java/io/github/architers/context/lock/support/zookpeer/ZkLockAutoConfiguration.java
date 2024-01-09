package io.github.architers.context.lock.support.zookpeer;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * zk分布式自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZkLockAutoConfiguration {


    @Bean
    public ZookeeperLockFactory zookeeperLockFactory() {
        return new ZookeeperLockFactory();
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework(ZookeeperProperties zookeeperProperties) {
        return this.buildCuratorFramework(zookeeperProperties);
    }

    private CuratorFramework buildCuratorFramework(ZookeeperProperties zookeeperProperties) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                //连接地址  集群用,隔开
                .connectString(zookeeperProperties.getServerLists())
                .connectionTimeoutMs(zookeeperProperties.getConnectionTimeoutMs())
                //会话超时时间
                .sessionTimeoutMs(zookeeperProperties.getSessionTimeoutMs())
                //设置重试机制
                //.retryPolicy(new ExponentialBackoffRetry(sleepMsBetweenRetry, maxRetries))
                //设置命名空间 在操作节点的时候，会以这个为父节点
                .namespace(zookeeperProperties.getNamespace())
                .build();
       // client.start();
        return client;
    }
}
