package com.lz.core.excel.html.parser;


import com.alibaba.excel.util.StringUtils;
import com.lz.core.excel.html.entity.HtmlSheet;
import com.lz.core.excel.html.entity.XlsCell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    List<List<XlsCell>> parse(HtmlSheet htmlSheet);


    /**
     * 描述：重新构建html
     *
     * @param html 需要转换的html
     * @return 构建后的html
     */
    String reBuildHtml(String html);
}
