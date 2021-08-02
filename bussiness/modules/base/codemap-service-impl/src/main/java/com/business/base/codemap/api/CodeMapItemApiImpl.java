package com.business.base.codemap.api;


import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyi
 * 代码项API接口实现层
 */
@RestController
public class CodeMapItemApiImpl implements CodeMapItemApi {
    private CodeMapItemService codeMapItemService;

    @Override
    public List<CodeMapItem> findByConvertCode(@PathVariable(name = "convertCode") String convertCode) {
        return codeMapItemService.findByConvertCode(convertCode);
    }


    @Autowired
    public void setCodeConvertItemService(CodeMapItemService codeMapItemService) {
        this.codeMapItemService = codeMapItemService;
    }
}
