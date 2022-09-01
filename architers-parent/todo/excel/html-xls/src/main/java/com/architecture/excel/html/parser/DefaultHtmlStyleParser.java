package io.github.architers.excel.html.parser;

import io.github.architers.excel.html.emun.HtmlType;

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
