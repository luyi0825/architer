package com.business.search.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 */
@ConfigurationProperties("customize.es")
public class ElasticSearchProperties {
    private List<EsHost> hosts;
    /**
     * 默认的数据分片数
     */
    private int defaultShards = 3;
    /**
     * 默认的数据备份数
     */
    private int defaultReplicas = 1;

    public List<EsHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<EsHost> hosts) {
        this.hosts = hosts;
    }

    public int getDefaultShards() {
        return defaultShards;
    }

    public void setDefaultShards(int defaultShards) {
        this.defaultShards = defaultShards;
    }

    public int getDefaultReplicas() {
        return defaultReplicas;
    }

    public void setDefaultReplicas(int defaultReplicas) {
        this.defaultReplicas = defaultReplicas;
    }



}
