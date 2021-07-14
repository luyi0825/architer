package com.business.search.ddl.service.impl;

import com.business.search.ddl.MappingType;
import com.business.search.ddl.dao.IndexMappingDao;
import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.IndexMapping;
import com.business.search.ddl.service.MappingService;
import com.core.module.common.exception.ParamsValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
@Service
public class MappingServiceImpl implements MappingService {
    private final IndexMappingDao indexMappingDao;

    @Autowired
    public MappingServiceImpl(IndexMappingDao indexMappingDao) {
        this.indexMappingDao = indexMappingDao;
    }

    /**
     * 创建mapping
     *
     * @param indexMapping
     * @return
     * @throws IOException
     */
    @Override
    public boolean createMapping(IndexMapping indexMapping) throws IOException {
        //判断索引是否已经存在
        if (indexMappingDao.exists(indexMapping.getIndex())) {
            return false;
        }
        //创建index
        indexMappingDao.createIndex(indexMapping.getIndex(),null,null);
        List<MappingItem> mappingItems = indexMapping.getMappingItems();
        Map<String, Object> mapping = new HashMap<>(mappingItems.size());
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
            Map<String, Object> properties = new HashMap<>(1);
            properties.put("message", message);
            mapping.put("properties", properties);
        });
        return indexMappingDao.putMapping(indexMapping.getIndex(), mapping);
    }
}
