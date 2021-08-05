package com.architecture.ultimate.nacosdiscovery;

import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * nacos服务发现配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@LoadBalancerClients(defaultConfiguration = NacosWeightLoadBalancerConfig.class)
public class NacosDiscoveryConfig {


}
