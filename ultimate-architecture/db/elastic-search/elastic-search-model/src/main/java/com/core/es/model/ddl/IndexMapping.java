package com.core.es.model.ddl;

import com.core.es.model.ddl.MappingItem;

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
