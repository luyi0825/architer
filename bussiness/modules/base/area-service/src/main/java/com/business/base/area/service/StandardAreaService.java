package com.business.base.area.service;


import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.business.base.area.entity.StandardArea;

import java.util.List;


/**
 * @author luyi
 * 标准区划service
 */
public interface StandardAreaService extends BaseService<StandardArea> {
    /**
     * 通过父级ID查询区划
     *
     * @param parentId 父级ID
     * @return parentID下的子区划
     */
    List<StandardArea> findByParentId(int parentId);

}
