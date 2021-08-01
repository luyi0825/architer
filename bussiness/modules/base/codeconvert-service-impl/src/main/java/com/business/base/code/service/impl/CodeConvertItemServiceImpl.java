package com.business.base.code.service.impl;


import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.code.entity.CodeConvertItem;
import com.business.base.code.service.CodeConvertItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luyi
 * 代码集项service实现类
 */
@Service
public class CodeConvertItemServiceImpl extends BaseServiceImpl<CodeConvertItem> implements CodeConvertItemService {

    @Override
    public void deleteByConvertCode(String convertCode) {
        QueryWrapper<CodeConvertItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("convert_code", convertCode);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    public List<CodeConvertItem> findByConvertCode(String convertCode) {
        QueryWrapper<CodeConvertItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("convert_code", convertCode);
        return this.baseMapper.selectList(queryWrapper);
    }
}
