package com.business.message.template.api;

import com.business.message.template.entity.MessageLimit;
import com.business.message.template.service.MessageLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：消息限制控制层
 *
 * @author luyi
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/limit")
public class MessageLimitController {

    private MessageLimitService messageLimitService;


    /**
     * 得到信息限制
     *
     * @param templateCode 模板编码
     * @return 消息模板的限制配置
     */
    @GetMapping("/get/{templateCode}")
    public MessageLimit getMessageLimitByCode(@PathVariable(name = "templateCode") String templateCode) {
        return messageLimitService.getMessageLimitByTemplateCode(templateCode);
    }

    /**
     * 保存消息限制
     *
     * @param messageLimit 消息限制
     */
    @PostMapping("/save")
    public void saveMessageTemplate(@RequestBody MessageLimit messageLimit) {
        messageLimitService.saveMessageLimit(messageLimit);
    }

    @Autowired
    public void setMessageLimitService(MessageLimitService messageLimitService) {
        this.messageLimitService = messageLimitService;
    }
}
