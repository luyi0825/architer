package com.core.resttemplate;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(okhttp3.OkHttpClient.class)
//@ConditionalOnProperty("custom.rest-template.okhttp.enabled")
public class OkHttpClientConfigure {
    private OkHttpClient okHttpClient;




    @Bean
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
    public OkHttpClient client(OkHttpClientFactory httpClientFactory,
                               ConnectionPool connectionPool,
                               RestTemplateProperties restTemplateProperties) {
        boolean followRedirects = restTemplateProperties.isFollowRedirects();
        int connectTimeout = restTemplateProperties.getConnectionTimeout();
        this.okHttpClient = httpClientFactory
                .createBuilder(restTemplateProperties.isDisableSslValidation())
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .followRedirects(followRedirects).connectionPool(connectionPool).build();
        return this.okHttpClient;
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }


}
