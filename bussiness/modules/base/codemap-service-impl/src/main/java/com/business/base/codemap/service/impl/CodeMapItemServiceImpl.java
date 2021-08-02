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
    public void deleteByConvertCode(String convertCode) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("convert_code", convertCode);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    public List<CodeMapItem> findByConvertCode(String convertCode) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("convert_code", convertCode);
        return this.baseMapper.selectList(queryWrapper);
    }
}
