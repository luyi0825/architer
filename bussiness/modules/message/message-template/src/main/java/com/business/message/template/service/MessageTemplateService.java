package com.business.message.template.service;


import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.business.message.template.entity.MessageTemplate;

/**
 * @author luyi
 * @date 2021/1/22
 * 消息模板
 */
public interface MessageTemplateService extends BaseService<MessageTemplate> {
    /**
     * 通过code找到对应的消息模板
     *
     * @param templateCode 模板code
     * @return 消息模板配置信息
     */
    MessageTemplate getMessageTemplateByCode(String templateCode);

    /**
     * 删除消息模板
     *
     * @param templateCode 模板编码
     */
    void deleteMessageTemplate(String templateCode);

    /**
     * 添加消息模板
     *
     * @param messageTemplate 模板信息
     */
    void addMessageTemplate(MessageTemplate messageTemplate);

    /**
     * 修改消息模板
     *
     * @param messageTemplate 模板信息
     */
    void updateMessageTemplate(MessageTemplate messageTemplate);
}
