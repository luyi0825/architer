package io.github.architers.query.common.model;

/**
 * @author luyi
 * 排序
 */
public class OrderCondition {
    /**
     * 字段
     */
    private String field;
    /**
     * 降序，默认false（也就是升序）
     */
    private boolean desc = false;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
