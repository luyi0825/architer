package com.architecture.excel.html.emun;

/**
 * 描述：html类型
 *
 * @author luyi
 */
public enum HtmlType {
    /**
     * 默认的
     */
    DEFAULT("default");

    private String type;

    HtmlType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
