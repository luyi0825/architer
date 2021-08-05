package com.architecture.ultimate.nacosdiscovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
public class NacosLoadBalancerClientFactory extends LoadBalancerClientFactory {
    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
            Environment environment,
            NacosDiscoveryProperties discoveryProperties,
            NacosServiceManager nacosServiceManager,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        //String name = loadBalancerClientFactory.getName(environment);
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NacosWeightReactiveLoadBalancer(discoveryProperties, nacosServiceManager, name);
    }


}
