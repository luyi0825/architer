package com.business.base.area.api.impl;

import com.business.base.area.api.StandardAreaApi;
import com.business.base.area.entity.StandardArea;
import com.business.base.area.service.StandardAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyi
 * 标准区划接口实现层
 */
@RestController
public class StandardAreaApiImpl implements StandardAreaApi {

    private StandardAreaService standardAreaService;

    @Override
    public List<StandardArea> findByParentId(String parentId) {
        return standardAreaService.findByParentId(parentId);
    }

    @Autowired
    public void setStandardAreaService(StandardAreaService standardAreaService) {
        this.standardAreaService = standardAreaService;
    }
}
