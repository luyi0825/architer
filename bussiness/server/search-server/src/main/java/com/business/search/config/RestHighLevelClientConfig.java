package com.business.search.config;

import com.business.search.properties.ElasticSearchProperties;
import com.business.search.properties.EsHost;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class RestHighLevelClientConfig {

    private final ElasticSearchProperties elasticSearchProperties;

    RestHighLevelClientConfig(ElasticSearchProperties elasticSearchProperties) {
        this.elasticSearchProperties = elasticSearchProperties;
    }


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
        List<EsHost> hosts = elasticSearchProperties.getHosts();
        if (CollectionUtils.isEmpty(hosts)) {
            throw new IllegalArgumentException("es hosts is null");
        }
        List<HttpHost> httpHosts = new ArrayList<>(hosts.size());
        hosts.forEach(host -> httpHosts.add(new HttpHost(host.getIp(), host.getPort(), host.getScheme())));
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
        return new RestHighLevelClient(restClientBuilder);
    }
}
