package com.core.es.model.doc;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luyi
 * 每条文档响应的数据
 */
public class DocumentResponse extends LinkedHashMap<String, Object> implements Serializable {
    public DocumentResponse(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public DocumentResponse(int initialCapacity) {
        super(initialCapacity);
    }

    public DocumentResponse() {
    }

    public DocumentResponse(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public DocumentResponse(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

}
