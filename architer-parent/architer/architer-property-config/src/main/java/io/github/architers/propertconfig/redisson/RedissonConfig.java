package io.github.architers.propertconfig.redisson;


import org.redisson.config.*;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;


/**
 * redisson配置类
 *
 * @author luyi
 * @since 1.0.3
 */
public class RedissonConfig extends Config implements Serializable {
    /**
     * 主从配置
     */
    @NestedConfigurationProperty
    private MasterSlaveServersConfig masterSlaveServersConfig;

    /**
     * 哨兵配置
     */
    @NestedConfigurationProperty
    private SentinelServersConfig sentinelServersConfig;
    /**
     * 单节点配置
     */
    @NestedConfigurationProperty
    private SingleServerConfig singleServerConfig;

    /**
     * 集群配置
     */
    @NestedConfigurationProperty
    private ClusterServersConfig clusterServersConfig;

    /**
     * 分片配置
     */
    @NestedConfigurationProperty
    private ReplicatedServersConfig replicatedServersConfig;

    private int threads = 16;

    private int nettyThreads = 32;


    public RedissonConfig() {
        super();
    }


    @Override
    public MasterSlaveServersConfig getMasterSlaveServersConfig() {
        return masterSlaveServersConfig;
    }

    @Override
    public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveServersConfig) {
        super.setMasterSlaveServersConfig(masterSlaveServersConfig);
        this.masterSlaveServersConfig = masterSlaveServersConfig;
    }

    @Override
    public SentinelServersConfig getSentinelServersConfig() {
        return sentinelServersConfig;
    }

    @Override
    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        super.setSentinelServersConfig(sentinelServersConfig);
        this.sentinelServersConfig = sentinelServersConfig;
    }

    @Override
    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    @Override
    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    @Override
    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    @Override
    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    @Override
    public ReplicatedServersConfig getReplicatedServersConfig() {
        return replicatedServersConfig;
    }

    @Override
    public void setReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) {
        this.replicatedServersConfig = replicatedServersConfig;
    }


    @Override
    public int getThreads() {
        return super.getThreads();
    }

    public Config setThreads(int threads) {
        super.setThreads(threads);
        this.threads = threads;
        return this;
    }


    public Config setNettyThreads(int nettyThreads) {
        super.setNettyThreads(nettyThreads);
        this.nettyThreads = nettyThreads;
        return this;
    }

    @Override
    public int getNettyThreads() {
        return super.getNettyThreads();
    }


}
