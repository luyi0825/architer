package com.core.excel.html.parser;

import com.core.excel.html.emun.HtmlType;

/**
 * @author luyi
 * 默认的样式解析器
 */
public class DefaultHtmlStyleParser implements HtmlStyleParser {
    @Override
    public String getHtmlType() {
        return HtmlType.DEFAULT.getType();
    }
}
