package com.business.base.codemap.service.impl;


import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.module.common.exception.ServiceException;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.service.CodeMapItemService;
import com.business.base.codemap.service.CodeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author luyi
 * 代码集service实现类
 */
@Service
public class CodeMapServiceImpl extends BaseServiceImpl<CodeMap> implements CodeMapService {

    private CodeMapItemService codeMapItemService;

    @Override
    public int insert(CodeMap entity) {
        entity.setCreateTime(new Date());
        QueryWrapper<CodeMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", entity.getCode());
        CodeMap dbCodeMap = this.baseMapper.selectOne(queryWrapper);
        if (dbCodeMap != null) {
            throw new ParamsValidException(MessageFormat.format("编码【{0}】已经存在", entity.getCode()));
        }
        return super.insert(entity);
    }

    @Override
    public int delete(Serializable id) {
        CodeMap codeMap = this.getById(id);
        if (codeMap == null) {
            throw new ServiceException("代码集不存在");
        }
        int itemSize = codeMapItemService.countByCode(codeMap.getCode());
        if (itemSize > 0) {
            throw new ServiceException("存在代码集项，无法删除");
        }
        int count = this.baseMapper.deleteById(id);
        if (count != 1) {
            throw new ServiceException("删除代码集失败");
        }
        return count;
    }

    @Autowired
    public void setCodeMapItemService(CodeMapItemService codeMapItemService) {
        this.codeMapItemService = codeMapItemService;
    }
}
