package com.business.base.code.service;


import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.business.base.code.entity.CodeConvertItem;
import lombok.Data;

import java.util.Date;

/**
 * @author luyi
 * 代码集项service接口类
 */
public interface CodeConvertItemService extends BaseService<CodeConvertItem> {

    /**
     * 通过convertCode删除代码项
     *
     * @param convertCode codeConvert的编码
     */
    void deleteByConvertCode(String convertCode);
}
