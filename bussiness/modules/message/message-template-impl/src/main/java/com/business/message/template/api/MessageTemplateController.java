package com.business.message.template.api;


import com.architecture.context.valid.group.AddGroup;
import com.architecture.context.valid.group.UpdateGroup;
import com.architecture.mybatisplus.QueryParam;
import com.architecture.query.common.model.Pagination;
import com.business.message.template.entity.MessageTemplate;
import com.business.message.template.service.MessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：消息模板控制层
 *
 * @author luyi
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/template")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 添加模板
     *
     * @param messageTemplate
     */
    @PostMapping("/add")
    public void addMessageTemplate(@RequestBody @Validated(value = {AddGroup.class}) MessageTemplate messageTemplate) {
        messageTemplateService.addMessageTemplate(messageTemplate);
    }

    /**
     * 更新模板
     *
     * @param messageTemplate
     */
    @PostMapping("/update")
    public void updateMessageTemplate(@RequestBody @Validated(value = {UpdateGroup.class}) MessageTemplate messageTemplate) {
        messageTemplateService.updateMessageTemplate(messageTemplate);
    }

    /**
     * 分页查询
     *
     * @param queryParams 分页查询参数
     * @return 分页数据
     */
    @PostMapping("/page")
    public Pagination page(@RequestBody QueryParam<MessageTemplate> queryParams) {
        return messageTemplateService.pageQuery(queryParams);
    }

    /**
     * 删除模板
     *
     * @param templateCode 模板编码
     */
    @DeleteMapping("/delete/{templateCode}")
    public void deleteMessageTemplate(@PathVariable String templateCode) {
        Assert.notNull(templateCode, "模板编码不能为空");
        messageTemplateService.deleteMessageTemplate(templateCode);
    }

    @GetMapping("/get/{templateCode}")
    public MessageTemplate getMessageTemplate(@PathVariable(name = "templateCode") String templateCode) {
        return messageTemplateService.getMessageTemplateByCode(templateCode);
    }

}
