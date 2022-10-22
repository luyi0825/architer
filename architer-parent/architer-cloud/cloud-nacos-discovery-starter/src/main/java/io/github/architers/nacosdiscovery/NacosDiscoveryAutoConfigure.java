package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceAutoConfiguration;
import com.alibaba.cloud.nacos.NacosServiceManager;
import io.github.architers.cloud.common.LoadBalanceProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@ConditionalOnNacosDiscoveryEnabled
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LoadBalanceProperties.class)
@ImportAutoConfiguration(NacosServiceAutoConfiguration.class)
@LoadBalancerClients(defaultConfiguration = LoadBalancerClientConfiguration.class)
public class NacosDiscoveryAutoConfigure {
    @Bean
    @ConditionalOnMissingBean
    public NacosServiceSelector nacosServiceSelector(NacosDiscoveryProperties discoveryProperties,
                                                     NacosServiceManager nacosServiceManager,
                                                     LoadBalanceProperties loadBalanceProperties) {
        return new NacosServiceSelector(discoveryProperties,
                nacosServiceManager,
                loadBalanceProperties);
    }

}
