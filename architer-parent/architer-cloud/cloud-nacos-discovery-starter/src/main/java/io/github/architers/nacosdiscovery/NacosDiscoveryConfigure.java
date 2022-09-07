package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import io.github.architers.nacosdiscovery.loadbalace.NacosWeightLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author luyi
 * nacos权重负载均衡配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnNacosDiscoveryEnabled
@EnableConfigurationProperties(NacosDiscoveryLoadProperties.class)
@LoadBalancerClients(defaultConfiguration = NacosDiscoveryConfigure.class)
@Slf4j
public class NacosDiscoveryConfigure {
    /**
     * 权重的负载均衡
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cloud.architer.load-balance", name = "strategy", havingValue = "weight", matchIfMissing = true)
    public ReactorServiceInstanceLoadBalancer reactorServiceInstanceLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        log.info("NacosWeightLoadBalance init");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new NacosWeightLoadBalance(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }

    /**
     * 随机
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cloud.architer.load-balance", name = "strategy", havingValue = "random")
    public RandomLoadBalancer randomLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        log.info("RandomLoadBalancer init");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }

    /**
     * 轮训
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cloud.architer.load-balance", name = "strategy", havingValue = "round")
    public RoundRobinLoadBalancer roundRobinLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        log.info("RoundRobinLoadBalancer init");
        return new RoundRobinLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }

    @Bean
    public DiscoveryClient nacosDiscoveryClient(NacosDiscoveryProperties discoveryProperties,
                                                NacosDiscoveryLoadProperties loadProperties,
                                                NacosServiceManager nacosServiceManager) {
        return new NacosDiscoveryClient(discoveryProperties, loadProperties, nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties()));
    }


}
