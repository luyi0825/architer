package com.business.base.code.api;

import com.business.base.code.entity.CodeConvert;
import com.business.base.code.service.CodeConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
public class CodeConvertApiImpl implements CodeConvertApi {
    private CodeConvertService codeConvertService;

    @Override
    public void add(CodeConvert codeConvert) {
        codeConvertService.insert(codeConvert);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public CodeConvert update(Long id, CodeConvert codeConvert) {
        return null;
    }

    @Autowired
    public void setCodeConvertService(CodeConvertService codeConvertService) {
        this.codeConvertService = codeConvertService;
    }
}
