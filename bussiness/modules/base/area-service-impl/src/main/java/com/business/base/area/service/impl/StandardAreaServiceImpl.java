package com.business.base.area.service.impl;

import com.architecture.ultimate.module.common.exception.ServiceException;
import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.area.entity.StandardArea;
import com.business.base.area.service.StandardAreaService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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

    /**
     * 重写baseService的删除
     */
    @Override
    public int delete(Serializable id) {
        List<StandardArea> sonList = this.findByParentId((Integer) id);
        if (sonList.size() > 0) {
            throw new ServiceException("该区划下存在节点，无法删除");
        }
        return this.baseMapper.deleteById(id);
    }
}
