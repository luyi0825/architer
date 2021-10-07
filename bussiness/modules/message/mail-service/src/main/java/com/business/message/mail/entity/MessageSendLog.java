package com.business.message.mail.entity;

import lombok.Data;

/**
 * 描述：消息发送日志
 *
 * @author luyi
 * @date 2021/3/19
 */
@Data
public class MessageSendLog {
    private Long id;
    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 发送的IP(请求人的IP)
     */
    private String sendIp;

    /**
     * 内容
     */
    private String content;


}
