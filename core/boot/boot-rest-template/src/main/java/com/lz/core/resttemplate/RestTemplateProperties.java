package com.lz.core.resttemplate;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * RestTemplate的配置文件
 *
 * @author luyi
 * @see org.springframework.cloud.openfeign.support.FeignHttpClientProperties  模仿的这个类
 */
@ConfigurationProperties(prefix = "custom.rest-template")
public class RestTemplateProperties {
    /**
     * 默认使用okHttp
     */
    private boolean okhttp = true;
    /**
     * 默认开启HttpClient连接池
     */
    private boolean httpclient = true;
    /**
     * 默认最大连接数
     */
    private int maxConnections = 100;
    /**
     * 存活时间
     */
    private Long timeToLive = 900L;
    /**
     * 存活时间的单位
     */
    private TimeUnit timeToLiveUnit = TimeUnit.SECONDS;

    private int connectionTimeout = 2000;

    private boolean followRedirects = true;

    private boolean disableSslValidation = false;

    public boolean isDisableSslValidation() {
        return disableSslValidation;
    }

    public void setDisableSslValidation(boolean disableSslValidation) {
        this.disableSslValidation = disableSslValidation;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public TimeUnit getTimeToLiveUnit() {
        return timeToLiveUnit;
    }

    public void setTimeToLiveUnit(TimeUnit timeToLiveUnit) {
        timeToLiveUnit = timeToLiveUnit;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public boolean isOkhttp() {
        return okhttp;
    }

    public void setOkhttp(boolean okhttp) {
        this.okhttp = okhttp;
    }

    public boolean isHttpclient() {
        return httpclient;
    }

    public void setHttpclient(boolean httpclient) {
        this.httpclient = httpclient;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
}
