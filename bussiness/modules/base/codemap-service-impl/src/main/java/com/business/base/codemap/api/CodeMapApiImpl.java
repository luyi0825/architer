package com.business.base.codemap.api;

import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.service.CodeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 * * 代码集对应的api接口层实现层
 */
@RestController
public class CodeMapApiImpl implements CodeMapApi {
    private CodeMapService codeMapService;

    @Override
    public void add(CodeMap codeMap) {
        codeMapService.insert(codeMap);
    }

    @Override
    public CodeMap update(Long id, CodeMap codeMap) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }


    @Autowired
    public void setCodeConvertService(CodeMapService codeMapService) {
        this.codeMapService = codeMapService;
    }
}
