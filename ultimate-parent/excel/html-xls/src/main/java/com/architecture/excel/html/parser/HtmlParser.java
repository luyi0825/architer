package com.architecture.excel.html.parser;


import com.architecture.excel.html.entity.HtmlSheet;
import com.architecture.excel.html.entity.XlsSheet;

/**
 * 描述：将hmlt转为XlsCell实体
 *
 * @author luyi
 */
public interface HtmlParser {


    /**
     * 解析单元格
     *
     * @param htmlSheet sheet的html信息
     * @return xls单元格对象集合
     */
    XlsSheet parse(HtmlSheet htmlSheet);


    /**
     * 描述：重新构建html
     *
     * @param html 需要转换的html
     * @return 构建后的html
     */
    String reBuildHtml(String html);
}
