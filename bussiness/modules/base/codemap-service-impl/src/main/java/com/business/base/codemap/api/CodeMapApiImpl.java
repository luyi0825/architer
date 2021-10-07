package com.business.base.codemap.api;

import com.architecture.query.common.model.Pager;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.service.CodeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author luyi
 * 代码集对应的api接口层实现层
 */
@RestController
public class CodeMapApiImpl implements CodeMapApi {
    private CodeMapService codeMapService;

    @Override
    public CodeMap getById(long id) {
        return codeMapService.getById(id);
    }

    @Override
    public void add(CodeMap codeMap) {
        codeMapService.insert(codeMap);
    }

    @Override
    public void update(Long id, CodeMap codeMap) {
        codeMap.setId(id);
        codeMap.setUpdateTime(new Date());
        codeMapService.updateById(codeMap);
    }

    @Override
    public void delete(Long id) {
        codeMapService.delete(id);
    }

    @Override
    public List<CodeMap> page(Pager pager) {
        return null;
    }


    @Autowired
    public void setCodeConvertService(CodeMapService codeMapService) {
        this.codeMapService = codeMapService;
    }
}
