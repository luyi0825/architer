package io.github.architers.server.file.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import io.github.architers.context.exception.BusErrorException;
import io.github.architers.context.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EasyExcelCheckRowReadListener implements  ReadListener<Map<String,Object>>{
    private final int startRow;

    private final int endRow;

    private final List<LinkedHashMap<Integer, String>> headDataList;


    public EasyExcelCheckRowReadListener(int startRow, int endRow) {
        this.startRow = startRow;
        this.endRow = endRow;
        headDataList = new ArrayList<>(endRow - startRow + 1);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("读取头失败");
        throw exception;
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        addCheckHeads(context);
        System.out.println(headMap);
    }

    @Override
    public void invoke(Map<String,Object> data, AnalysisContext context) {
        if (context.readRowHolder().getRowIndex() > 100) {
            throw new BusErrorException("没有读取到头数据");
        }
        addCheckHeads(context);
    }

    private void addCheckHeads(AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowNumber = context.readRowHolder().getRowIndex() + 1;
        if (startRow <= rowNumber && endRow >= rowNumber) {
            Map<Integer, Cell> readCellDataLinkedHashMap = readRowHolder.getCellMap();
            LinkedHashMap<Integer, String> headDataMap = new LinkedHashMap<>();
            readCellDataLinkedHashMap.forEach((key, cellData) -> {
                ReadCellData<?> readCellData = (ReadCellData<?>) cellData;
                String stringValue = null;
                if (readCellData != null) {
                    stringValue = readCellData.getStringValue();
                }
                headDataMap.put(key, stringValue);
            });
            headDataList.add(headDataMap);
            System.out.println(rowNumber);
        }
    }

    public List<LinkedHashMap<Integer, String>> getHeadDataList() {
        return headDataList;
    }

    public String getBase64RowData(){
        byte[] rowBytes = JsonUtils.toJsonBytes(this.getHeadDataList());
        return org.apache.commons.codec.binary.Base64.encodeBase64String(rowBytes);
    }



    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        ReadListener.super.extra(extra, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        int rowNumber = context.readRowHolder().getRowIndex() + 1;
        if (rowNumber <= endRow) {
            return true;
        }
        return false;
    }
}
