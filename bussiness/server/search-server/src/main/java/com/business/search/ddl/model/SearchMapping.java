package com.business.search.ddl.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * 查询引擎的mapping
 */
public class SearchMapping implements Serializable {
    /**
     * index项
     */
    private String index;
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


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<MappingItem> getMappingItems() {
        return mappingItems;
    }

    public void setMappingItems(List<MappingItem> mappingItems) {
        this.mappingItems = mappingItems;
    }
}
