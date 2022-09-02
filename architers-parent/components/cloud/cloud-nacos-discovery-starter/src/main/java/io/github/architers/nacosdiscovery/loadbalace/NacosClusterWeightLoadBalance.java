package io.github.architers.nacosdiscovery.loadbalace;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * nacos基于集群权重的负载均衡
 * <li>首先在集群中环境中进行负载均衡，如果集群中没有示例，则从集群外中选取示例</li>
 *
 * @author luyi
 */
public class NacosClusterWeightLoadBalance implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosServiceManager nacosServiceManager;

    public NacosClusterWeightLoadBalance(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager, String serviceId) {
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.serviceId = serviceId;
    }

    @SneakyThrows
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        Properties properties = discoveryProperties.getNacosProperties();
        String clusterName = discoveryProperties.getClusterName();
        NamingService namingService = nacosServiceManager.getNamingService(properties);
        Instance instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName));
        if (instance == null) {
            instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup());
        }
        ServiceInstance serviceInstance = NacosServiceDiscovery.hostToServiceInstance(instance, serviceId);
        if (serviceInstance == null) {
            return Mono.just(new EmptyResponse());
        }
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
