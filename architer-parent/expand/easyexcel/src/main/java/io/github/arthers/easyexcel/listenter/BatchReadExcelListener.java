package io.github.arthers.easyexcel.listenter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ModelBuildEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * easyExcel批量监听
 * <li>支持从指定行读取数据</li>
 * <li>支持批量数据大小</li>
 * <li>支持自定义异常处理</li>
 *
 * @author luyi
 * @since 1.0.3
 */
@Slf4j
public class BatchReadExcelListener<T extends Serializable> extends ModelBuildEventListener {

    /**
     * 一次读取数据的数量
     */
    private int batchSize = 1000;

    /**
     * 数据开始行(注意是excel的行数)
     */
    private int readDataRow = 1;

    /**
     * 总的行数量
     */
    private int totalRowNum;

    /**
     * 读取的数据
     */
    private List<T> dataList;

    /**
     * 错误的信息
     */
    private List<ReadExceptionInfo> readExceptionInfos;


    private Consumer<List<T>> consumer;

    private Consumer<List<ReadExceptionInfo>> readExceptionInfoConsumer = null;


    public BatchReadExcelListener() {

    }


    public BatchReadExcelListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
    }

    /**
     * 每读取一条数据时执行
     *
     * @param cellDataMap 每条数据
     * @param context     analysisContext
     */
    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellDataMap, AnalysisContext context) {
        int row = context.readRowHolder().getRowIndex() + 1;
        if (row < readDataRow) {
            log.info("第{}行忽略（需要从{}行读取）", row, readDataRow);
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>(batchSize);
        }
        super.invoke(cellDataMap, context);
        Object data = context.readRowHolder().getCurrentRowAnalysisResult();
        dataList.add((T) data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= batchSize) {
            consumer.accept(dataList);
            dataList.clear();
            if (readExceptionInfoConsumer != null) {
                readExceptionInfoConsumer.accept(readExceptionInfos);
                readExceptionInfos.clear();
            }
        }
        totalRowNum++;
    }

    public BatchReadExcelListener<T> setConsumer(Consumer<List<T>> consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * 最后读取完数据时执行
     *
     * @param analysisContext analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (dataList != null && !dataList.isEmpty()) {
            consumer.accept(dataList);
        }
        if (readExceptionInfos != null) {
            readExceptionInfoConsumer.accept(readExceptionInfos);
        }
        log.info("成功解析{}条数据", totalRowNum);
    }

    /**
     * 处理数据读取时捕获的异常
     *
     * @param exception 异常信息
     * @param context   context
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        ReadExceptionInfo readExceptionInfo = new ReadExceptionInfo();
        readExceptionInfo.setRow(readRowHolder.getRowIndex() + 1);
        readExceptionInfo.setErrorMessage(exception.getMessage());
        readExceptionInfo.setOriginalException(exception);
        if (readExceptionInfoConsumer == null) {
            throw new RuntimeException("第" + readExceptionInfo.getRow() + "行,错误信息：" + readExceptionInfo.getErrorMessage());
        }
        if (readExceptionInfos == null) {
            readExceptionInfos = new LinkedList<>();
        }
        readExceptionInfos.add(readExceptionInfo);
    }


    public BatchReadExcelListener<T> setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public void setReadDataRow(int readDataRow) {
        this.readDataRow = readDataRow;
    }

    public BatchReadExcelListener<T> setReadExceptionInfoConsumer(Consumer<List<ReadExceptionInfo>> readExceptionInfoConsumer) {
        this.readExceptionInfoConsumer = readExceptionInfoConsumer;
        return this;
    }


}
