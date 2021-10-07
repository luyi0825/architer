package com.business.base.codemap.service;


import com.architecture.mybatisplus.service.BaseService;
import com.business.base.codemap.entity.CodeMapItem;

import java.util.List;

/**
 * @author luyi
 * 代码集项service接口类
 */
public interface CodeMapItemService extends BaseService<CodeMapItem> {

    /**
     * 通过code删除代码项
     *
     * @param code 代码集的code
     */
    void deleteByCode(String code);

    /**
     * 通过代码code统计代码项数量
     *
     * @param code 代码级code
     * @return 代码级数量
     */
    List<CodeMapItem> findByCode(String code);

    /**
     * 通过代码code统计代码项数量
     *
     * @param code 代码级code
     * @return 代码级数量
     */
    int countByCode(String code);
}
