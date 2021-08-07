package com.business.base.codemap.api;


import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author luyi
 * 代码项API接口实现层
 */
@RestController
public class CodeMapItemApiImpl implements CodeMapItemApi {
    private CodeMapItemService codeMapItemService;

    @Override
    public List<CodeMapItem> findByCode(@PathVariable(name = "code") String code) {
        return codeMapItemService.findByCode(code);
    }

    @Override
    public void add(CodeMapItem codeMapItem) {
        codeMapItem.setCreateTime(new Date());
        codeMapItemService.insert(codeMapItem);
    }

    @Override
    public void update(CodeMapItem codeMapItem) {
        codeMapItem.setUpdateTime(new Date());
        codeMapItemService.updateById(codeMapItem);
    }

    @Override
    public CodeMapItem getById(Long id) {
        return codeMapItemService.getById(id);
    }

    @Autowired
    public void setCodeConvertItemService(CodeMapItemService codeMapItemService) {
        this.codeMapItemService = codeMapItemService;
    }
}
