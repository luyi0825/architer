package com.lz.core.excel.html;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.lz.core.excel.html.entity.HtmlSheet;
import com.lz.core.excel.html.entity.XlsCell;
import com.lz.core.excel.html.parser.HtmlParser;
import org.apache.poi.sl.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.util.List;

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
        List<List<XlsCell>> xlsCellList = htmlParser.parse(htmlSheet);
        EasyExcel.write(outputStream).sheet(htmlSheet.getSheetName()).registerConverter(new XlsCellConverter())
                .registerWriteHandler(new CellMergeStrategy(xlsCellList))
                .doWrite(xlsCellList);

    }


    @Autowired
    public void setHtmlParser(HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
    }
}
