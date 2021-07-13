package com.business.search.ddl.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * 查询引擎的mapping
 */
public class SearchMapping implements Serializable {
    private String index;
    private List<MappingItem> mappingItems;

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
