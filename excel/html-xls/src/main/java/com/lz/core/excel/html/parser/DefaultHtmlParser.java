package com.lz.core.excel.html.parser;


import com.lz.core.excel.html.entity.HtmlSheet;
import com.lz.core.excel.html.entity.XlsCell;
import com.lz.core.excel.html.exception.HtmlTypeException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luyi
 * 默认的html解析器
 */
public class DefaultHtmlParser implements HtmlParser {

    /**
     * 样式解析器,防止HtmlStyleParser过多：
     * 1.使用适配器模式循环的次数可能太多，影响效率
     * 2.如果直接使用实现类直接解析，外部的接口太杂太多
     * 所以放入Map中处理
     */
    public Map<String, HtmlStyleParser> htmlStyleParserMap;

    public DefaultHtmlParser(List<HtmlStyleParser> htmlStyleParsers) {
        htmlStyleParserMap = new HashMap<>(htmlStyleParsers.size());
        htmlStyleParsers.forEach(htmlStyleParser -> {
            //得到html类型，并放入字符串常量池
            String htmlType = htmlStyleParser.getHtmlType().intern();
            if (StringUtils.isEmpty(htmlType)) {
                throw new HtmlTypeException("htmlType is null!");
            }
            htmlStyleParserMap.putIfAbsent(htmlType, htmlStyleParser);
        });
    }

    /**
     * 解析
     *
     * @param htmlSheet
     * @return
     */
    @Override
    public List<List<XlsCell>> parse(HtmlSheet htmlSheet) {
        //重新构建html
        String html = reBuildHtml(htmlSheet.getHtml());
        Document doc = Jsoup.parse(html);
        Elements trElements = doc.getElementsByTag("tr");
        if (CollectionUtils.isEmpty(trElements)) {
            return null;
        }
        return parseRowElements(trElements);
    }


    public List<List<XlsCell>> parseRowElements(Elements trElements) {
        List<List<XlsCell>> xlsCells = new ArrayList<>();
        //合并的单元格
        List<XlsCell> mergeCellList = new ArrayList<>();
        //所在行：存在一行全是hide的表单或者有一行全是合并单元格的行,所以必须要记住行
        AtomicInteger rowNum = new AtomicInteger(0);
        for (Element tr : trElements) {
            List<Element> colElements = tr.getElementsByTag("td");
            if (CollectionUtils.isEmpty(colElements)) {
                // 处理表头
                colElements = tr.getElementsByTag("th");
            }
            int currentRowNum = rowNum.get();

            List<XlsCell> colXlsCells = this.parseColElements(colElements, mergeCellList, rowNum);

            if (!CollectionUtils.isEmpty(colXlsCells)) {
                xlsCells.add(colXlsCells);
            }
            // 说明行都被合并了
            int preMergeRow = rowNum.get() - currentRowNum;
            //先前都被合并的行补充空数据
            while (preMergeRow > 0) {
                xlsCells.add(new ArrayList<>());
                preMergeRow--;
            }
            rowNum.addAndGet(1);
        }
        return xlsCells;
    }

    private List<XlsCell> parseColElements(List<Element> colElements, List<XlsCell> mergeCellList, AtomicInteger rowNum) {
        List<XlsCell> colXlsCells = new ArrayList<>(colElements.size());
        AtomicInteger colIndex = new AtomicInteger(-1);
        int rowIndex = rowNum.get();
        boolean merge = false;
        for (Element element : colElements) {
            XlsCell xlsCell = buildXlsCell(element, colIndex, rowIndex);
            if (xlsCell == null) {
                continue;
            }
            //处理合并的单元格
            if (dealMergeCell(mergeCellList, rowIndex, colIndex, xlsCell)) {
                merge = true;
            }
            //处理样式
            // parseCellClass(element, xlsCell);
            colXlsCells.add(xlsCell);
        }
        //解决这行都被其他的行合并
        if (merge) {
            int minEndRow = getMinEndRow(colXlsCells);
            rowNum.set(minEndRow);
        }
        return colXlsCells;
    }

    /**
     * 描述：构建XlsCell
     *
     * @author luyi
     */
    private XlsCell buildXlsCell(Element element, AtomicInteger colIndex, int rowIndex) {
        // 解析单元格class属性
        String cellClass = element.attr("class");
        //忽略
        if (ignoreCell(cellClass)) {
            return null;
        }
        String rowspanStr = element.attr("rowspan");
        String colspanStr = element.attr("colspan");
        int rowspan = 1, colspan = 1;
        if (!StringUtils.isEmpty(rowspanStr)) {
            rowspan = Integer.parseInt(rowspanStr);
        }
        if (!StringUtils.isEmpty(colspanStr)) {
            colspan = Integer.parseInt(colspanStr);
        }
        XlsCell xlsCell = new XlsCell();
        xlsCell.setText(element.text());
        xlsCell.setRowspan(rowspan);
        xlsCell.setColspan(colspan);

        //设置开始列，结束列
        xlsCell.setStartCol(colIndex.addAndGet(1));
        colIndex.addAndGet(colspan > 0 ? colspan - 1 : 0);
        xlsCell.setEndCol(colIndex.get());
        //设置开始行、结束行
        xlsCell.setStartRow(rowIndex);
        int endRow = rowIndex + (rowspan > 0 ? rowspan - 1 : 0);
        xlsCell.setEndRow(endRow);
        return xlsCell;
    }

    /**
     * 描述：处理合并的单元格
     *
     * @author luyi
     */
    private boolean dealMergeCell(List<XlsCell> mergeList, int rowIndex, AtomicInteger colIndex, XlsCell xlsCell) {
        //判断该位置是否存在合并单元格的情况
        for (XlsCell mergeCell : mergeList) {
            int col = colIndex.get();
            if (mergeCell.getStartRow() <= rowIndex && mergeCell.getEndRow() >= rowIndex
                    && mergeCell.getStartCol() <= col && mergeCell.getEndCol() >= col) {
                col = colIndex.addAndGet(mergeCell.getColspan());
                xlsCell.setStartCol(col);
                //结束列需要-1（colspan是1，实际不加列）
                int colspan = xlsCell.getColspan();
                xlsCell.setEndCol(col + (colspan > 0 ? colspan - 1 : 0));
            }
        }
        //记住合并单元格的位置
        if (xlsCell.getRowspan() > 1 || xlsCell.getColspan() > 1) {
            mergeList.add(xlsCell);
            return true;
        }
        return false;
    }


    @Override
    public String reBuildHtml(String html) {
        return html;
    }

    /**
     * 描述：解析excel 样式
     *
     * @param element 需要解析的节点
     * @param xlsCell 解析的cell信息
     * @author luyi
     * @date 2021/3/17
     */

    /**
     * 描述：忽略这个cell
     *
     * @author luyi
     * @date 2021/3/16
     */
    protected boolean ignoreCell(String cellClass) {
        if (StringUtils.isEmpty(cellClass)) {
            return false;
        }
        //对隐藏列跳过
        return cellClass.contains("hidden") || cellClass.contains("undisplay") || cellClass.contains("hide")
                || cellClass.contains("rowHeader");
    }

    /**
     * 描述：得到最小的行
     *
     * @author luyi
     * @date 2021/3/16
     */
    private int getMinEndRow(List<XlsCell> xlsCells) {
        int minEndRow = Integer.MAX_VALUE;
        for (XlsCell xlsCell : xlsCells) {
            if (xlsCell.getEndRow() >= 0) {
                minEndRow = Math.min(minEndRow, xlsCell.getEndRow());
            }
        }
        return minEndRow;
    }
}
