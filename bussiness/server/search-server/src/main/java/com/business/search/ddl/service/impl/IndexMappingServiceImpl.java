package com.business.search.ddl.service.impl;

import com.business.search.ddl.MappingType;
import com.business.search.ddl.dao.IndexMappingDao;
import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.IndexMapping;
import com.business.search.ddl.service.IndexMappingService;
import com.core.module.common.exception.ParamsValidException;
import com.core.module.common.exception.ServiceException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
@Service
public class IndexMappingServiceImpl implements IndexMappingService {
    private final IndexMappingDao indexMappingDao;
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    public IndexMappingServiceImpl(IndexMappingDao indexMappingDao) {
        this.indexMappingDao = indexMappingDao;
    }

    @Override
    public boolean createIndexMapping(IndexMapping indexMapping) throws IOException {
        if (indexMappingDao.exists(indexMapping.getIndex())) {
            throw new ServiceException(MessageFormat.format("index[{0}] exists", indexMapping.getIndex()));
        }
        Map<String, Object> mapping = this.getMapping(indexMapping.getMappingItems());
        return indexMappingDao.createIndex(indexMapping.getIndex(), null, mapping);
    }

    @Override
    public boolean rebuildIndexMapping(IndexMapping indexMapping) throws IOException {
        String index = indexMapping.getIndex();
        CountRequest countRequest = new CountRequest(index);
        if (indexMappingDao.exists(indexMapping.getIndex())) {
            long count = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT).getCount();
            if (count > 0) {
                throw new ServiceException(MessageFormat.format("index[{0}]存在数据，无法重构)", index));
            }
            if (!indexMappingDao.deleteIndex(indexMapping.getIndex())) {
                return false;
            }
        }
        Map<String, Object> mapping = this.getMapping(indexMapping.getMappingItems());
        return indexMappingDao.createIndex(indexMapping.getIndex(), null, mapping);
    }

    /**
     * 通过mapping项得到mapping信息
     *
     * @param mappingItems mapping字段定义项
     * @return mapping定义
     */
    private Map<String, Object> getMapping(List<MappingItem> mappingItems) {
        Map<String, Object> mapping = new HashMap<>(mappingItems.size());
        Map<String, Object> properties = new HashMap<>(1);
        mappingItems.forEach(mappingItem -> {
            String field = mappingItem.getField();
            if (StringUtils.isEmpty(field)) {
                throw new ParamsValidException("field is null");
            }
            MappingType mappingType = mappingItem.getMappingType();
            if (mappingType == null) {
                throw new ParamsValidException("MappingType is null");
            }
            Map<String, Object> message = new HashMap<>(1);
            message.put("type", mappingType.getType());
            properties.put(field, message);
        });
        mapping.put("properties", properties);
        return mapping;
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
