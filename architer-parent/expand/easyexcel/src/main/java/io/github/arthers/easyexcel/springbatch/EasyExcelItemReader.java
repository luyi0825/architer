package io.github.arthers.easyexcel.springbatch;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * easyExcel对springBatch的支持
 * <hr>
 * <p>流程(读写是两个不同的线程):</p>
 * <p>1.数据会从指定行（readDataRow）从excel读取到中转队列（中转队列是一个阻塞队列，队列满了，就put阻塞，直到有数据消费）</p>
 * <p>2.消息者（doRead方法）会从队列中获取数据，然后消费</p>
 * <br>
 *
 * <hr>
 *
 * @author luyi
 * @version 1.0.3
 */
public class EasyExcelItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements ReadListener<T> {

    private Executor parserExcelExecutor;


    /**
     * 从哪一行数据读取(excel的行数)
     */
    private volatile int readDataRow = 1;

    /**
     * 中转数据队列大小
     */
    private int transitDataQueueSize = 200;

    /**
     * 中转数据队列
     */
    protected volatile BlockingQueue<T> transitDataQueue;

    /**
     * 是否已经读取
     */
    private final AtomicBoolean startRead = new AtomicBoolean(false);

    /**
     * 是否结束读取
     * <li>发生异常，终止</li>
     * <li>正常读取完</li>
     */
    private volatile boolean endRead = false;

    /**
     * easyExcel读取的reader信息
     */
    private ExcelReaderBuilder excelReaderBuilder;

    /**
     * 需要读取的sheet
     */
    private List<ReadSheet> readSheetList;

    /**
     * 读取发生的异常
     */
    private volatile Exception exception;


    public EasyExcelItemReader() {
        setName(ClassUtils.getShortName(EasyExcelItemReader.class));
    }


    @Override
    protected void jumpToItem(int itemIndex) {
        readDataRow = readDataRow + itemIndex;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(excelReaderBuilder, "excelReaderBuilder不能为空");
        Assert.notNull(readSheetList, "读取的readSheetList不能为空");
        checkTransitDataQueueSize();
        transitDataQueue = new LinkedBlockingDeque<>(transitDataQueueSize);
        super.open(executionContext);
    }

    @Override
    protected void doOpen() {
        Thread openThread = Thread.currentThread();
        Assert.notNull(parserExcelExecutor, "解析线程池不能为空");
        if (startRead.compareAndSet(false, true)) {
            parserExcelExecutor.execute(() -> {
                ExcelReader excelReader = null;
                try {
                    //防止由openThread执行，造成transitDataQueue,在put的时候阻塞:
                    //   默认情况write数据执行springBatch任务的线程，read是新开的线程，
                    //   如果线程池满了拒绝策略交给springBatch任务处理的线程，当put阻塞就会造成当前线程一直阻塞
                    //   ==>想消费线程被put的地方阻塞了，想put队列又满了，GG
                    if (openThread == Thread.currentThread()) {
                        throw new RuntimeException("线程池任务full");
                    }
                    excelReader = excelReaderBuilder.registerReadListener(this).build();
                    excelReader.read(readSheetList).readAll();
                } catch (Exception e) {
                    endRead = true;
                    exception = e;
                } finally {
                    IOUtils.closeQuietly(excelReader);
                }
            });
        }

    }

    @Override
    protected void doClose() {

    }


    @Override
    protected T doRead() throws Exception {
        T t = null;
        //循环从queue中读取数据
        while (t == null) {
            if (exception != null) {
                throw exception;
            }
            if (!endRead) {
                //等待,直到有数据
                t = transitDataQueue.poll(20, TimeUnit.MILLISECONDS);
            } else {
                if (transitDataQueue.isEmpty()) {
                    if (exception != null) {
                        //数据读取完了，有异常就抛出异常(异常end)
                        throw exception;
                    } else {
                        //正常读取完毕(正常end)
                        return null;
                    }
                }
                //有数据直接读取(正常end->end了数据没有消费完)
                t = transitDataQueue.poll();
            }
        }
        if (exception != null) {
            throw exception;
        }
        return t;
    }


    @Override
    public void setName(String name) {
        super.setName(this.getClass().getSimpleName());
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        ReadRowHolder readRowHolder = context.readRowHolder();
        //忽略（1.最开始从n读取|2.中间失败重新读取
        if (readDataRow > 0 && (readRowHolder.getRowIndex() + 1) < readDataRow) {
            return;
        }
        try {
            //插入,直到有数据消费
            transitDataQueue.put((T) readRowHolder.getCurrentRowAnalysisResult());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        endRead = true;
    }


    public void setParserExcelExecutor(Executor executor) {
        this.parserExcelExecutor = parserExcelExecutor;
    }

    public void setReadDataRow(int readDataRow) {
        this.readDataRow = readDataRow;
    }

    public void setExcelReaderBuilder(ExcelReaderBuilder excelReaderBuilder) {
        this.excelReaderBuilder = excelReaderBuilder;
    }

    public void setReadSheetList(List<ReadSheet> readSheetList) {
        this.readSheetList = readSheetList;
    }

    public void setTransitDataQueueSize(int transitDataQueueSize) {
        this.transitDataQueueSize = transitDataQueueSize;
        checkTransitDataQueueSize();
    }

    private void checkTransitDataQueueSize() {
        Assert.isTrue(transitDataQueueSize < 0 || transitDataQueueSize > 2000,
                "transitDataQueueSize" +
                        "有误");
    }
}
