package io.github.architers.nacosdiscovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import io.github.architers.cloud.common.LoadBalanceProperties;
import io.github.architers.cloud.common.VisitCluster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NacosServiceSelector extends NacosServiceDiscovery {


    private NacosDiscoveryProperties discoveryProperties;


    private NamingService namingService;

    private final LoadBalanceProperties loadBalanceProperties;


    public NacosServiceSelector(NacosDiscoveryProperties discoveryProperties,
                                 NacosServiceManager nacosServiceManager,
                                LoadBalanceProperties loadBalanceProperties) {
        super(discoveryProperties,nacosServiceManager);
        this.loadBalanceProperties = loadBalanceProperties;
        this.namingService = nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
        this.discoveryProperties = discoveryProperties;

    }

    public List<ServiceInstance> getInstances(String serviceId) {
        String clusterName = discoveryProperties.getClusterName();
        //判断集群方式
        VisitCluster visitCluster = loadBalanceProperties.getVisitCluster();
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
        if (instances.size() > 0 && loadBalanceProperties.isGrayscaleRelease()) {
            String releaseVersion = loadBalanceProperties.getReleaseVersion();
            instances = instances.stream().filter(e -> releaseVersion.equals(e.getMetadata().get("metadata.release-version")))
                    .collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(instances)) {
            throw new RuntimeException("get Nacos instances exception");
        }
        return hostToServiceInstanceList(instances, serviceId);
    }
}
