package com.business.message.mail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.business.message.mail.dao.MessageLimitDao;
import com.business.message.mail.entity.MessageLimit;
import com.business.message.mail.service.MessageLimitService;
import com.core.mybatisplus.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 描述：消息发送限制业务实现层
 *
 * @author luyi
 * @date 2021/3/10
 */
@Service
public class MessageLimitServiceImpl extends BaseServiceImpl<MessageLimit> implements MessageLimitService {


    @Override
    public MessageLimit getMessageLimitByTemplateCode(String templateCode) {
        QueryWrapper<MessageLimit> queryWrapper = new QueryWrapper<MessageLimit>().eq("template_code", templateCode);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveMessageLimit(MessageLimit messageLimit) {
        QueryWrapper<MessageLimit> queryWrapper = new QueryWrapper<MessageLimit>().eq("template_code", messageLimit.getTemplateCode());
        MessageLimit limit = this.baseMapper.selectOne(queryWrapper);
        if (limit == null) {
            //添加
            this.baseMapper.insert(messageLimit);
        } else {
            //修改
            this.baseMapper.update(messageLimit, queryWrapper);
        }
    }
}
