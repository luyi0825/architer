package io.github.architers.nacosdiscovery.loadbalace;


;
import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


/**
 * nacos基于集群权重的负载均衡
 * <li>首先在集群中环境中进行负载均衡，如果集群中没有示例，则从集群外中选取示例</li>
 *
 * @author luyi
 */
public class NacosWeightLoadBalance implements ReactorServiceInstanceLoadBalancer {

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public NacosWeightLoadBalance(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }


    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map((serviceInstances) -> this.processInstanceResponse(supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        List<Pair<ServiceInstance>> hostsWithWeight = new ArrayList<>();
        for (ServiceInstance serviceInstance : instances) {
            hostsWithWeight.add(new Pair<ServiceInstance>(serviceInstance, Double.parseDouble(serviceInstance.getMetadata().get("nacos.weight"))));
        }
        Chooser<String, ServiceInstance> vipChooser = new Chooser<>("www.taobao.com");
        vipChooser.refresh(hostsWithWeight);
        ServiceInstance serviceInstance = vipChooser.randomWithWeight();
        return new DefaultResponse(serviceInstance);
    }
}
