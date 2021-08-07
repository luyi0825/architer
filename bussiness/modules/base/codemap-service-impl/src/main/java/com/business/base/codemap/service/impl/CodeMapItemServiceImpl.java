package com.business.base.codemap.service.impl;


import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.codemap.constants.CodeMapItemValidConstant;
import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author luyi
 * 代码集项service实现类
 */
@Service
public class CodeMapItemServiceImpl extends BaseServiceImpl<CodeMapItem> implements CodeMapItemService {


    @Override
    public int insert(CodeMapItem entity) {
        QueryWrapper<CodeMapItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", entity.getCode());
        queryWrapper.eq("item_code", entity.getItemCode());
        int count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ParamsValidException(MessageFormat.format(CodeMapItemValidConstant.ITEM_CODE_EXIST, entity.getItemCode()));
        }
        entity.setCreateTime(new Date());
        return this.baseMapper.insert(entity);
    }

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
