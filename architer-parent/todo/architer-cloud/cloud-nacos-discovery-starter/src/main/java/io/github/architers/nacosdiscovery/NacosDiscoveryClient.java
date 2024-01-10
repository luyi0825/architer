package io.github.architers.nacosdiscovery;


import com.alibaba.nacos.api.exception.NacosException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;


/**
 * nacos服务发现
 * <li>弃用了naocs的NacosDiscoveryClient（不满足自己的要求）</li>
 *
 * @author luyi
 */
@RefreshScope
@Slf4j
public class NacosDiscoveryClient implements DiscoveryClient {

    /**
     * Nacos Discovery Client Description.
     */
    public static final String DESCRIPTION = "Spring Cloud Nacos Discovery Client";

    private final NacosServiceSelector nacosServiceSelector;

    public NacosDiscoveryClient(NacosServiceSelector nacosServiceSelector) {
        this.nacosServiceSelector = nacosServiceSelector;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        return nacosServiceSelector.getInstances(serviceId);
    }


    @Override
    public List<String> getServices() {
        try {
            return nacosServiceSelector.getServices();
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
