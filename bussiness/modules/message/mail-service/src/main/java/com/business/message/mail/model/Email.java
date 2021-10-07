package com.business.message.mail.model;


import lombok.Data;

/**
 * @author ly
 * email的信息
 */
@Data
public class Email {
    /**
     * 模板code
     */
    private String templateCode;
    /**
     * 接收人,多个用逗号分割
     */
    private String receivers;

    private String content;

    private String subject;

    private String ip;

}
