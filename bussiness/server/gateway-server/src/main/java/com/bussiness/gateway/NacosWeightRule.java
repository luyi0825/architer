//package com.ly.gateway;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.ribbon.NacosServer;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
//import com.netflix.loadbalancer.Server;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//
///**
// * @author ly
// * @date 2020/12/13
// * 功能：开启nacos权重负载均衡
// */
////@Configuration
//public class NacosWeightRule extends AbstractLoadBalancerRule {
//
//    private static final Log log = LogFactory.getLog(NacosWeightRule.class);
//
//    @Autowired
//    private NacosDiscoveryProperties discoveryProperties;
//
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//
//    private static AtomicInteger atomicInteger;
//
//    public static void main(String[] args) {
//        System.out.println(Integer.MAX_VALUE);
//    }
//
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig clientConfig) {
//
//    }
//
//    @Override
//    public Server choose(Object key) {
//        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
//        //服务名称
//        String name = loadBalancer.getName();
//        try {
//            //组名称
//            String groupName = discoveryProperties.getGroup();
//            Instance instance = discoveryProperties.namingServiceInstance()
//                    .selectOneHealthyInstance(name, groupName);
//            log.info("选中的instance = {}" + instance);
//            System.out.println(instance.getMetadata());
//            /*
//             * instance转server的逻辑参考自：
//             * org.springframework.cloud.alibaba.nacos.ribbon.NacosServerList.instancesToServerList
//             */
//            return new NacosServer(instance);
//
//        } catch (NacosException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
