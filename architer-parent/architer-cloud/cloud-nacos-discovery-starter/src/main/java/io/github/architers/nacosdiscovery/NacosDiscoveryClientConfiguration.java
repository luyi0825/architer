package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import io.github.architers.cloud.common.LoadBalanceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.ConditionalOnBlockingDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * nacos权重负载均衡配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnNacosDiscoveryEnabled
@EnableConfigurationProperties(LoadBalanceProperties.class)
@LoadBalancerClients(defaultConfiguration = NacosDiscoveryClientConfiguration.class)
@Slf4j
@ConditionalOnDiscoveryEnabled
@ConditionalOnBlockingDiscoveryEnabled
@AutoConfigureBefore({SimpleDiscoveryClientAutoConfiguration.class,
        CommonsClientAutoConfiguration.class})
@AutoConfigureAfter(NacosDiscoveryAutoConfiguration.class)
public class NacosDiscoveryClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NacosServiceSelector nacosServiceSelector(NacosDiscoveryProperties discoveryProperties,
                                                     NacosServiceManager nacosServiceManager,
                                                     LoadBalanceProperties loadBalanceProperties) {
        return new NacosServiceSelector(discoveryProperties,
                nacosServiceManager,
                loadBalanceProperties);
    }

    @Bean
    public DiscoveryClient nacosDiscoveryClient(NacosServiceSelector nacosServiceSelector) {
        return new NacosDiscoveryClient(nacosServiceSelector);
    }


}
