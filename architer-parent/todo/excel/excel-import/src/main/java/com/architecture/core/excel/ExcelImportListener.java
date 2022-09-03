package io.github.architers.core.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Map;

/**
 * @author luyi
 */
public class ExcelImportListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 实体类
     */
    private Class<?> entityClazz;

    /**
     * 一次处理的数量
     */
    private int batchSize = -1;




    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {

        ExcelImportListener excelImportListener=new ExcelImportListener();
        data.forEach(e -> {

        });
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        Map<Integer, Cell> cellMap = context.readWorkbookHolder().getReadWorkbook().
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
