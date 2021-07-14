package com.business.search.doc.service;

import java.io.IOException;
import java.util.Map;

/**
 * 数据查询的service
 *
 * @author luyi
 */
public interface DocService {
    public Object queryList(String index) throws IOException;

    /**
     * @param index
     * @param docs
     * @throws IOException
     */
    void put(String index, Map<String, Object> docs) throws IOException;
}
