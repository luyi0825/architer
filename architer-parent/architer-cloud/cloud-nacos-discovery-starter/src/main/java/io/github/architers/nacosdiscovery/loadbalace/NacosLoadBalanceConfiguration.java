package io.github.architers.nacosdiscovery.loadbalace;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 负载均衡配置
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnNacosDiscoveryEnabled
@Slf4j
public class NacosLoadBalanceConfiguration {

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
}
