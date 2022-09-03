package io.github.architers.nacosdiscovery;

import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * nacos服务发现配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@LoadBalancerClients(defaultConfiguration = io.github.architers.nacosdiscovery.NacosLoadBalancerConfig.class)
public class NacosDiscoveryConfig {


}