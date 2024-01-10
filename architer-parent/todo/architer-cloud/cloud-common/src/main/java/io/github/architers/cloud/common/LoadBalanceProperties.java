package io.github.architers.cloud.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * nacos服务发现负载属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "spring.cloud.architer.load-balance")
@Data
public class LoadBalanceProperties {

    /**
     * 负载均衡策略(默认为权重)
     */
    private LoadBalanceStrategy strategy = LoadBalanceStrategy.weight;

    /**
     * 访问的集群
     */
    private VisitCluster visitCluster = VisitCluster.current;

    /**
     * 是否启用灰色发布
     */
    private boolean grayscaleRelease;

    /**
     * 灰度发布字段标识
     */
    private String releaseVersion = "1.0.0";
}
