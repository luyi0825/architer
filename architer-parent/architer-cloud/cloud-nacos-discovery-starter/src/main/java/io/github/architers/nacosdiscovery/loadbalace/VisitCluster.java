package io.github.architers.nacosdiscovery.loadbalace;

/**
 * 访问的集群
 */
public enum VisitCluster {
    /**
     * 所有集群
     */
    all,
    /**
     * 当前集群(实现多机房分区)
     */
    current,
    /**
     * 优先当前集群（当前集群的所有实例跌机，则访问其他集群的数据，保证高可用）
     */
    priority

}
