package com.business.base.codemap.service;


import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.business.base.codemap.entity.CodeMapItem;

import java.util.List;

/**
 * @author luyi
 * 代码集项service接口类
 */
public interface CodeMapItemService extends BaseService<CodeMapItem> {

    /**
     * 通过convertCode删除代码项
     *
     * @param convertCode codeConvert的编码
     */
    void deleteByConvertCode(String convertCode);

    List<CodeMapItem> findByConvertCode(String convertCode);
}
