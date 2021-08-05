//package com.architecture.ultimate.nacosdiscovery;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.NacosServiceInstance;
//import com.alibaba.cloud.nacos.NacosServiceManager;
//import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.*;
//import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerClientRequestTransformer;
//import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
//import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Properties;
//
//
//public class NacosReactorLoadBalancerExchangeFilterFunction extends ReactorLoadBalancerExchangeFilterFunction {
//
//    @Autowired
//    private NacosDiscoveryProperties discoveryProperties;
//
//    @Autowired
//    private NacosServiceManager nacosServiceManager;
//
//
//
//    public NacosReactorLoadBalancerExchangeFilterFunction(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory, LoadBalancerProperties properties, List<LoadBalancerClientRequestTransformer> transformers) {
//        super(loadBalancerFactory, properties, transformers);
//    }
//
//    @SneakyThrows
//    @Override
//    protected Mono<Response<ServiceInstance>> choose(String serviceId, Request<RequestDataContext> request) {
//        Properties properties = discoveryProperties.getNacosProperties();
//        NamingService namingService = nacosServiceManager.getNamingService(properties);
//        Instance instance = namingService.selectOneHealthyInstance(serviceId, discoveryProperties.getGroup());
//        ServiceInstance serviceInstance = NacosServiceDiscovery.hostToServiceInstance(instance, serviceId);
//        if (serviceInstance == null) {
//            return Mono.just(new EmptyResponse());
//        }
//        return Mono.just(new DefaultResponse(serviceInstance));
//    }
//}
