package com.core.es.component;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luyi
 * ES组件配置类
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class EsComponentConfig {

    private ElasticSearchProperties elasticSearchProperties;


    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        List<EsNode> hosts = elasticSearchProperties.getNodes();
        if (CollectionUtils.isEmpty(hosts)) {
            throw new IllegalArgumentException("es nodes is null");
        }
        List<HttpHost> httpHosts = new ArrayList<>(hosts.size());
        hosts.forEach(host -> httpHosts.add(new HttpHost(host.getIp(), host.getPort(), host.getScheme())));
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
        return new RestHighLevelClient(restClientBuilder);
    }

    @Autowired
    public void setElasticSearchProperties(ElasticSearchProperties elasticSearchProperties) {
        this.elasticSearchProperties = elasticSearchProperties;
    }
}
