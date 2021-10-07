package com.business.message.template.service;


import com.architecture.mybatisplus.service.BaseService;
import com.business.message.template.entity.MessageLimit;


/**
 * 描述：消息模板发送限制
 *
 * @author luyi
 * @date 2021/3/10
 */
public interface MessageLimitService extends BaseService<MessageLimit> {

    /**
     * 描述：通过模板编码得到消息限制配置
     *
     * @param templateCode 模板编码
     * @return 消息限制
     * @date 2021/3/11
     */
    MessageLimit getMessageLimitByTemplateCode(String templateCode);

    /**
     * 保存消息限制
     * 修改和添加都是这一个接口
     *
     * @param messageLimit 消息限制信息
     */
    void saveMessageLimit(MessageLimit messageLimit);
}
