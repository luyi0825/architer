package com.business.base.area.service.impl;

import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.area.entity.StandardArea;
import com.business.base.area.service.StandardAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luyi
 * 标准区划service对应的实现类
 */
@Service
public class StandardAreaServiceImpl extends BaseServiceImpl<StandardArea> implements StandardAreaService {
    @Override
    public List<StandardArea> findByParentId(int parentId) {
        QueryWrapper<StandardArea> queryMapper = new QueryWrapper<>();
        queryMapper.eq("parent_id", parentId);
        return this.baseMapper.selectList(queryMapper);
    }
}
