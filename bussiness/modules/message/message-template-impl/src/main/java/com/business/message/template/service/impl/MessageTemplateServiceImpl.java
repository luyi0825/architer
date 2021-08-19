package com.business.message.template.service.impl;

import com.architecture.context.common.exception.ServiceException;
import com.architecture.mybatisplus.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.business.message.template.entity.MessageTemplate;
import com.business.message.template.service.MessageTemplateService;

import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplate> implements MessageTemplateService {

    /**
     * 描述：得到消息模板
     *
     * @TODO 需要缓存
     * @author luyi
     * @date 2021/3/11
     */
    @Override
    public MessageTemplate getMessageTemplateByCode(String templateCode) {
        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_code", templateCode);
        return this.baseMapper.selectOne(queryWrapper);
    }


    @Override
    public void deleteMessageTemplate(String templateCode) {
        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_code", templateCode);
        //@TODO 后续考虑能否删除的情况
        int count = this.baseMapper.delete(queryWrapper);
        if (count != 1) {
            throw new ServiceException("删除失败");
        }
    }

    @Override
    public void addMessageTemplate(MessageTemplate messageTemplate) {
        MessageTemplate template = this.getMessageTemplateByCode(messageTemplate.getTemplateCode());
        if (template != null) {
            throw new ServiceException(messageTemplate.getTemplateCode() + "已经存在！");
        }
        int num = this.baseMapper.insert(messageTemplate);
        if (num != 1) {
            throw new ServiceException("添加失败");
        }
    }

    @Override
    public void updateMessageTemplate(MessageTemplate messageTemplate) {
        QueryWrapper<MessageTemplate> updateMapper = new QueryWrapper<>();
        updateMapper.eq("template_code", messageTemplate.getTemplateCode());
        int num = this.baseMapper.update(messageTemplate, updateMapper);
        if (num != 1) {
            throw new ServiceException("修改失败");
        }
    }
}
