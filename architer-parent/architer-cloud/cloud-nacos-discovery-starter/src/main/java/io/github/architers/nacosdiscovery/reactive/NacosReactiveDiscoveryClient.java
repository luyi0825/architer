package io.github.architers.nacosdiscovery.reactive;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import io.github.architers.nacosdiscovery.NacosServiceSelector;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Slf4j
public class NacosReactiveDiscoveryClient implements ReactiveDiscoveryClient {


    private NacosServiceSelector nacosServiceSelector;

    public NacosReactiveDiscoveryClient(NacosServiceSelector nacosServiceSelector) {
        this.nacosServiceSelector = nacosServiceSelector;
    }

    public String description() {
        return "Spring Cloud Nacos Reactive Discovery Client";
    }


    public Flux<ServiceInstance> getInstances(String serviceId) {
        return Mono.justOrEmpty(serviceId).flatMapMany(this.loadInstancesFromNacos()).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<String, Publisher<ServiceInstance>> loadInstancesFromNacos() {
        return (serviceId) -> {
            return Flux.fromIterable(this.nacosServiceSelector.getInstances(serviceId));
        };
    }

    public Flux<String> getServices() {
        return Flux.defer(() -> {
            try {
                return Flux.fromIterable(this.nacosServiceSelector.getServices());
            } catch (Exception var2) {
                log.error("get services from nacos server fail,", var2);
                return Flux.empty();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
