package com.architecture.excel.html;


import com.alibaba.excel.EasyExcel;
import com.architecture.excel.html.entity.HtmlSheet;
import com.architecture.excel.html.entity.XlsSheet;
import com.architecture.excel.html.parser.HtmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;

/**
 * 描述：html导出excel
 *
 * @author luyi
 * @date 2021/3/27
 */
public class Html2XlsExport {
    private final Logger logger = LoggerFactory.getLogger(Html2XlsExport.class);

    private HtmlParser htmlParser;

    public void export(HtmlSheet htmlSheet, OutputStream outputStream) {
        XlsSheet xlsSheet = htmlParser.parse(htmlSheet);
        EasyExcel.write(outputStream).sheet(htmlSheet.getSheetName()).registerConverter(new XlsCellConverter())
                .registerWriteHandler(new CellMergeStrategy(xlsSheet.getMergeCells()))
                .doWrite(xlsSheet.getCellList());

    }


    @Autowired
    public void setHtmlParser(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }
}
