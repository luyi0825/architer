package com.business.message.mail.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 * 发送限制配置
 * 根据实际业务增加限定
 */
@Data
@TableName("t_message_limit")
public class MessageLimit implements Serializable {
    /**
     * 模板代码
     */
    private String templateCode;
    /**
     * 日发送量
     */
    private Integer dayNum = 0;
    /**
     * 总共发送的量
     */
    private Integer totalNums = 0;
    /**
     * IP总共发送的量
     */
    private Integer ipNums = 0;
    /**
     * ip日发送量
     */
    private Integer ipDayNum = 0;
    /**
     * 是否记录日志内容
     */
    private boolean logContent;

    /**
     * 间隔时间：单位秒，在间隔时间内不能发送
     */
    public Integer intervals;

}
