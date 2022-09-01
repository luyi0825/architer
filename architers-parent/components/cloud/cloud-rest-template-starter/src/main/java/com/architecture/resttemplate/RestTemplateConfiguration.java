package io.github.architers.resttemplate;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * restTemplate 配置文件
 *
 * @author luyi
 * @date 2021/5/19
 */
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "custom.rest-template", value = "httpclient", matchIfMissing = true)
    @ConditionalOnMissingBean(ConnectionPool.class)
    public ConnectionPool httpClientConnectionPool(
            RestTemplateProperties httpClientProperties,
            OkHttpClientConnectionPoolFactory connectionPoolFactory) {
        int maxTotalConnections = httpClientProperties.getMaxConnections();
        Long timeToLive = httpClientProperties.getTimeToLive();
        TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
        return connectionPoolFactory.create(maxTotalConnections, timeToLive, ttlUnit);
    }

    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", value = "httpclient", matchIfMissing = true)
    public OkHttpClient client(OkHttpClientFactory httpClientFactory,
                               ConnectionPool connectionPool,
                               RestTemplateProperties restTemplateProperties) {
        boolean followRedirects = restTemplateProperties.isFollowRedirects();
        int connectTimeout = restTemplateProperties.getConnectionTimeout();
        return httpClientFactory
                .createBuilder(restTemplateProperties.isDisableSslValidation())
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .followRedirects(followRedirects).connectionPool(connectionPool).build();
    }

    @Bean
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", value = "httpclient", matchIfMissing = true)
    public ClientHttpRequestFactory clientHttpRequestFactory(OkHttpClient okHttpClient) {
        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
    }

    /**
     * 注入开启负载均衡，使用连接池的restTemplate
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", value = {"load-balanced", "httpclient"}, havingValue = "true")
    @LoadBalanced
    public RestTemplate loadBalancedHttpClientRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

    /**
     * 使用连接池的restTemplate
     */
    @Bean
    @ConditionalOnBean(ClientHttpRequestFactory.class)
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", name = "load-balanced", havingValue = "false")
    public RestTemplate httpClientRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

    /**
     * 使用开启负载均衡的restTemplate
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", name = "load-balanced", havingValue = "true")
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 使用普通的restTemplate
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnProperty(prefix = "custom.rest-template", name = "load-balanced", havingValue = "false")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
