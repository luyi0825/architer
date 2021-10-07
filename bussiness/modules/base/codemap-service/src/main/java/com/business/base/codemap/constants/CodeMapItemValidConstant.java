package com.business.base.codemap.constants;

/**
 * 代码集校验常量
 *
 * @author luyi
 */
public class CodeMapItemValidConstant {
    /**
     * 主键ID
     */
    public final static String ID_NOT_NULL = "ID不能为空";
    /**
     * code
     */
    public final static String CODE_NOT_BLANK = "代码集编码不能为空";

    /**
     * itemCode
     */
    public final static String ITEM_CODE_NOT_BLANK = "代码集项编码不能为空";
    public final static String ITEM_CODE_LENGTH_LIMIT = "代码集项编码长度不能超过30";
    public final static String ITEM_CODE_EXIST = "代码集项编码[{0}]已经存在";
    /**
     * caption
     */
    public final static String ITEM_CAPTION_NOT_BLANK = "代码集项中文名称不能为空";
    public final static String ITEM_CAPTION_LENGTH_LIMIT = "代码集项中文名称长度超过50";
    /**
     * remark
     */
    public final static String REMARK_LENGTH_LIMIT = "备注长度不能超过100";

}
