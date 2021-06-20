package com.core.mybatisplus;

/**
 * 排序字段
 */

public enum OrderEnum {

    /**
     * 排序
     */
    desc("desc"),
    asc("asc");

    private String order;

    OrderEnum(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
