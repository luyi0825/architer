package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import io.github.architers.nacosdiscovery.loadbalace.VisitCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * nacos服务发现
 * <li>弃用了naocs的NacosDiscoveryClient（不满足自己的要求）</li>
 *
 * @author luyi
 */
public class NacosDiscoveryClient implements DiscoveryClient {
    private static final Logger log = LoggerFactory.getLogger(com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient.class);

    /**
     * Nacos Discovery Client Description.
     */
    public static final String DESCRIPTION = "Spring Cloud Nacos Discovery Client";

    private final NacosDiscoveryProperties discoveryProperties;

    private final NamingService namingService;

    private final NacosDiscoveryLoadProperties loadProperties;

    public NacosDiscoveryClient(NacosDiscoveryProperties discoveryProperties,
                                NacosDiscoveryLoadProperties loadProperties,
                                NamingService namingService) {
        this.discoveryProperties = discoveryProperties;
        this.namingService = namingService;
        this.loadProperties = loadProperties;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        String clusterName = discoveryProperties.getClusterName();
        //判断集群方式
        VisitCluster visitCluster = loadProperties.getVisitCluster();
        List<Instance> instances = Collections.emptyList();
        try {
            if (VisitCluster.all.equals(visitCluster)) {
                //能够访问所有的集群
                instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), true);
            } else if (VisitCluster.current.equals(visitCluster)) {
                //当前集群
                instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName), true);
            } else if (VisitCluster.priority.equals(visitCluster)) {
                //优先当前
                instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), Collections.singletonList(clusterName), true);
                if (CollectionUtils.isEmpty(instances)) {
                    instances = namingService.selectInstances(serviceId, discoveryProperties.getGroup(), true);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("get Nacos instances exception");
        }
        //判断是否启动灰色发布
        if (instances.size() > 0 && loadProperties.isGrayscaleRelease()) {
            String release = discoveryProperties.getMetadata().get(loadProperties.getGrayscaleField());
            instances = instances.stream().filter(e -> release.equals(e.getMetadata().get(loadProperties.getGrayscaleField()))).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(instances)) {
            throw new RuntimeException("get Nacos instances exception");
        }

        return NacosServiceDiscovery.hostToServiceInstanceList(instances, serviceId);
    }


    @Override
    public List<String> getServices() {
        try {
            String group = discoveryProperties.getGroup();
            ListView<String> services = namingService.getServicesOfServer(1,
                    Integer.MAX_VALUE, group);
            return services.getData();
        } catch (Exception e) {
            log.error("get service name from nacos server fail,", e);
            return Collections.emptyList();
        }
    }
}
