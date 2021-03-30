package com.lz.core.excel.html.entity;


import com.lz.core.excel.html.emun.HtmlType;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述：sheet 对应的html信息,主要用来接收需要转换html信息
 *
 * @author luyi
 */
@Data
public class HtmlSheet implements Serializable {
    /**
     * sheet 名称
     */
    private String sheetName;
    /**
     * html字符串
     */
    private String html;
    /**
     * html类型
     */
    private String htmlType = HtmlType.DEFAULT.getType();
    /**
     * 额外的参数，主要用来做一些定制的需求
     */
    Map<String, Object> extraParams;


}
