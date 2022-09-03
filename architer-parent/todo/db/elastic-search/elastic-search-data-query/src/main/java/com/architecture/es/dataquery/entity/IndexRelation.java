package com.architecture.es.dataquery.entity;

/**
 * @author luyi
 * index的对应关系
 */
public class IndexRelation {
    /**
     * 数据库的index
     */
    private String dbIndex;
    /**
     * 暴露给接口的index
     */
    private String apiIndex;

    public String getDbIndex() {
        return dbIndex;
    }

    public IndexRelation setDbIndex(String dbIndex) {
        this.dbIndex = dbIndex;
        return this;
    }

    public String getApiIndex() {
        return apiIndex;
    }

    public IndexRelation setApiIndex(String apiIndex) {
        this.apiIndex = apiIndex;
        return this;
    }
}
