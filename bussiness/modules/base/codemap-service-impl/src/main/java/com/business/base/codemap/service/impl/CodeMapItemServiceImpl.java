package com.business.base.codemap.service.impl;


import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luyi
 * 代码集项service实现类
 */
@Service
public class CodeMapItemServiceImpl extends BaseServiceImpl<CodeMapItem> implements CodeMapItemService {

    @Override
    public void deleteByCode(String code) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    public List<CodeMapItem> findByCode(String convertCode) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", convertCode);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public int countByCode(String code) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return this.baseMapper.selectCount(queryWrapper);
    }
}
