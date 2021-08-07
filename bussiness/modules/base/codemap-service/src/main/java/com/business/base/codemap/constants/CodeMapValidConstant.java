package com.business.base.codemap.constants;

/**
 * 代码集校验常量
 *
 * @author luyi
 */
public class CodeMapValidConstant {
    /**
     * 主键ID
     */
    public final static String ID_NOT_NULL = "ID不能为空";
    /**
     * code
     */
    public final static String CODE_NOT_EMPTY = "编码不能为空";
    public final static String CODE_LENGTH_LIMIT = "编码长度在5~30之间";
    /**
     * caption
     */
    public final static String CAPTION_NOT_EMPTY = "中文描述不能为空";
    public final static String CAPTION_LENGTH_LIMIT = "中文描述长度不能超过50";
    /**
     * remark
     */
    public final static String REMARK_LENGTH_LIMIT = "备注长度不能超过100";

}
