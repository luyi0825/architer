package io.github.architers.nacosdiscovery.loadbalace;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.github.architers.nacosdiscovery.NacosDiscoveryLoadProperties;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * nacos基于集群权重的负载均衡
 * <li>首先在集群中环境中进行负载均衡，如果集群中没有示例，则从集群外中选取示例</li>
 *
 * @author luyi
 */
public class NacosClusterLoadBalance implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosDiscoveryLoadProperties loadProperties;

    private final NacosServiceManager nacosServiceManager;

    private ReactorLoadBalancer reactorLoadBalancer;

    public NacosClusterLoadBalance(NacosDiscoveryProperties discoveryProperties,
                                   NacosServiceManager nacosServiceManager,
                                   String serviceId,
                                   NacosDiscoveryLoadProperties loadProperties) {
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.serviceId = serviceId;
        this.loadProperties = loadProperties;
    }

    @SneakyThrows
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        Properties properties = discoveryProperties.getNacosProperties();
        String clusterName = discoveryProperties.getClusterName();
        NamingService namingService = nacosServiceManager.getNamingService(properties);
        //查询出该服务对应组所有的健康实例

        //判断集群方式
        VisitCluster visitCluster = loadProperties.getVisitCluster();
        List<Instance> instances = null;
        if (VisitCluster.all.equals(visitCluster)) {
            //能够访问所有的集群
            instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), true);
        } else if (VisitCluster.current.equals(visitCluster)) {
            //当前集群
            instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName), true);
        } else if (VisitCluster.priority.equals(visitCluster)) {
            //优先当前
            instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName), true);
        }
        //负载均衡策略
        LoadBalanceStrategy loadBalanceStrategy = loadProperties.getLoadBalanceStrategy();
        if (loadBalanceStrategy.equals(LoadBalanceStrategy.random)) {
            int index = ThreadLocalRandom.current().nextInt(instances.size());
            Instance instance = instances.get(index);
        }


        Instance instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName));
        if (instance == null) {
            instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup());
        }
        instance.getMetadata();
        ServiceInstance serviceInstance = NacosServiceDiscovery.hostToServiceInstance(instance, serviceId);
        if (serviceInstance == null) {
            return Mono.just(new EmptyResponse());
        }
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
