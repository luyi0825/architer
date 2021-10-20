package com.architecture.nacosdiscovery.loadbalace;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Properties;

/**
 * nacos基于权重的负载均衡
 * <li>从对应的组中选择一个健康的实例</li>
 *
 * @author luyi
 */

public class NacosWeightLoadBalance implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosServiceManager nacosServiceManager;

    public NacosWeightLoadBalance(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager, String serviceId) {
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.serviceId = serviceId;
    }

    @SneakyThrows
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        BeanDefinition.SCOPE_PROTOTYPE
        NamingService namingService = nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
        Instance instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup());
        ServiceInstance serviceInstance = NacosServiceDiscovery.hostToServiceInstance(instance, serviceId);
        if (serviceInstance == null) {
            return Mono.just(new EmptyResponse());
        }
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
