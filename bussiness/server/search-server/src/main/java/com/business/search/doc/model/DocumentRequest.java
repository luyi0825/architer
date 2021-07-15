package com.business.search.doc.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author luyi
 * 每条文档请求的数据
 */
public class DocumentRequest implements Serializable {
    private String id;
    private Map<String, Object> source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }
}
