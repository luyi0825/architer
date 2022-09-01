package io.github.architers.es.model.ddl;


import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * es的index定义，并包含mapping
 */
public class IndexMapping implements Serializable {
    /**
     * index项
     */
    private String dbIndex;
    /**
     * apiIndex
     */
    private String apiIndex;
    /**
     * 数据分片数
     */
    private Integer shards;
    /**
     * 数据备份数
     */
    private Integer replicas;

    /**
     * mapping定义
     */
    private List<MappingItem> mappingItems;

    public int getShards() {
        return shards;
    }

    public void setShards(int shards) {
        this.shards = shards;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }


    public String getDbIndex() {
        return dbIndex;
    }

    public IndexMapping setDbIndex(String dbIndex) {
        this.dbIndex = dbIndex;
        return this;
    }

    public String getApiIndex() {
        return apiIndex;
    }

    public IndexMapping setApiIndex(String apiIndex) {
        this.apiIndex = apiIndex;
        return this;
    }

    public IndexMapping setShards(Integer shards) {
        this.shards = shards;
        return this;
    }

    public IndexMapping setReplicas(Integer replicas) {
        this.replicas = replicas;
        return this;
    }

    public List<MappingItem> getMappingItems() {
        return mappingItems;
    }

    public void setMappingItems(List<MappingItem> mappingItems) {
        this.mappingItems = mappingItems;
    }


}
