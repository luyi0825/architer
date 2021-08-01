package com.business.base.code.service.impl;


import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.module.common.exception.ServiceException;
import com.architecture.ultimate.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.code.entity.CodeConvert;
import com.business.base.code.service.CodeConvertItemService;
import com.business.base.code.service.CodeConvertService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author luyi
 * 代码集service实现类
 */
@Service
public class CodeConvertServiceImpl extends BaseServiceImpl<CodeConvert> implements CodeConvertService {

    private CodeConvertItemService codeConvertItemService;

    @Override
    public int insert(CodeConvert entity) {
        entity.setCreateTime(new Date());
        QueryWrapper<CodeConvert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", entity.getCode());
        CodeConvert dbCodeConvert = this.baseMapper.selectOne(queryWrapper);
        if (dbCodeConvert != null) {
            throw new ParamsValidException(MessageFormat.format("编码【{0}】已经存在", entity.getCode()));
        }
        return super.insert(entity);
    }

    @Override
    public int delete(Serializable id) {
        CodeConvert codeConvert = this.getById(id);
        if (codeConvert == null) {
            throw new ServiceException("代码级不存在");
        }
        codeConvertItemService.deleteByConvertCode(codeConvert.getCode());
        return this.delete(id);
    }
}
