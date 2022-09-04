package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import io.github.architers.nacosdiscovery.loadbalace.NacosClusterWeightLoadBalance;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author luyi
 * nacos权重负载均衡配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
public class NacosLoadBalancerConfig extends LoadBalancerClientFactory {
    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
            Environment environment,
            NacosDiscoveryProperties discoveryProperties,
            NacosServiceManager nacosServiceManager) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NacosClusterWeightLoadBalance(discoveryProperties, nacosServiceManager, name);
    }


}
