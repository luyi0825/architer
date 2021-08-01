package com.business.base.code.api;


import com.business.base.code.entity.CodeConvertItem;
import com.business.base.code.service.CodeConvertItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyi
 * 代码项API接口实现层
 */
@RestController
public class CodeConvertItemApiImpl implements CodeConvertItemApi {
    private CodeConvertItemService codeConvertItemService;

    @Override
    public List<CodeConvertItem> findByConvertCode(@PathVariable(name = "convertCode") String convertCode) {
        return codeConvertItemService.findByConvertCode(convertCode);
    }


    @Autowired
    public void setCodeConvertItemService(CodeConvertItemService codeConvertItemService) {
        this.codeConvertItemService = codeConvertItemService;
    }
}
