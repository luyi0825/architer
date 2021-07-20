package com.architecture.ultimate.es.model.doc;

import java.io.Serializable;
import java.util.Map;

/**
 * @author luyi
 * 每条文档请求的数据
 */
public class DocumentRequest implements Serializable {
    private String id;
    private String index;
    private String requestType;
    private Map<String, Object> source;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

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
