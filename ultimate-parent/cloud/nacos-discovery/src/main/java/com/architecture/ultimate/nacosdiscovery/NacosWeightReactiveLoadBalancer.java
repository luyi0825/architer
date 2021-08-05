package com.architecture.ultimate.nacosdiscovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

import java.util.Properties;

/**
 * @author luyi
 * nacos的权重负载均衡器
 */
public class NacosWeightReactiveLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final String serviceId;

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosServiceManager nacosServiceManager;

    public NacosWeightReactiveLoadBalancer(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager, String serviceId) {
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.serviceId = serviceId;
    }

    @SneakyThrows
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        Properties properties = discoveryProperties.getNacosProperties();
        NamingService namingService = nacosServiceManager.getNamingService(properties);
        Instance instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup());
        ServiceInstance serviceInstance = NacosServiceDiscovery.hostToServiceInstance(instance, serviceId);
        if (serviceInstance == null) {
            return Mono.just(new EmptyResponse());
        }
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
