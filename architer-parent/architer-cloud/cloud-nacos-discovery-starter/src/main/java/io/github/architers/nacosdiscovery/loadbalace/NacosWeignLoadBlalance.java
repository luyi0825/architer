package io.github.architers.nacosdiscovery.loadbalace;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.nacos.client.utils.LogUtils.NAMING_LOGGER;

/**
 * nacos的权重的负载均衡
 */
public class NacosWeignLoadBlalance implements ReactorServiceInstanceLoadBalancer {


    private final String serviceId;


    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public NacosWeignLoadBlalance(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
                                  String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;

    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {

        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> hosts) {
        NAMING_LOGGER.debug("entry randomWithWeight");
        if (hosts == null || hosts.size() == 0) {
            NAMING_LOGGER.debug("hosts == null || hosts.size() == 0");
            return null;
        }
        NAMING_LOGGER.debug("new Chooser");
        List<Pair<Instance>> hostsWithWeight = new ArrayList<>();
        for (Instance host : hosts) {
            if (host.isHealthy()) {
                hostsWithWeight.add(new Pair<Instance>(host, host.getWeight()));
            }
        }
        NAMING_LOGGER.debug("for (Host host : hosts)");
        Chooser<String, Instance> vipChooser = new Chooser<>("www.taobao.com");
        vipChooser.refresh(hostsWithWeight);
        NAMING_LOGGER.debug("vipChooser.refresh");
        return vipChooser.randomWithWeight();

    }

    @Override
    public Mono<Response<ServiceInstance>> choose() {
        return ReactorServiceInstanceLoadBalancer.super.choose();
    }
}
