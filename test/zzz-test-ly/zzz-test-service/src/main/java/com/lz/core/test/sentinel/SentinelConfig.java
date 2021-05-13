package com.lz.core.test.sentinel;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * Sentinel 配置类
 */
@Configuration
public class SentinelConfig {

    //@Autowired
    //private NacosDiscoveryProperties nacosDiscoveryProperties;

    public SentinelConfig() {
        // remoteAddress 代表 Nacos 服务端的地址
// groupId 和 dataId 对应 Nacos 中相应配置
//        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>("124.71.110.204:8848", "DEFAULT_GROUP", "sentinel",
//                source -> JsonUtils.readListValue(source, FlowRule.class));
//        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());


    }

    @Bean
    public CustomeBlockExceptionHandler customeBlockExceptionHandler() {
        return new CustomeBlockExceptionHandler();
    }
}
