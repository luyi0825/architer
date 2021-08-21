package com.business.message.template;

import com.architecture.context.module.SubModule;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author luyi
 */
@MapperScan("com.business.message.template.dao")
@SubModule(name="message-template",caption = "消息服务-模板模块")
public class MessageTemplateModule {
}
