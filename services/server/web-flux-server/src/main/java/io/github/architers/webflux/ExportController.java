package io.github.architers.webflux;

import io.github.architers.context.web.PostMapping;
import io.github.architers.server.file.domain.dto.FileTaskParam;
import io.github.architers.server.file.domain.dto.TaskRecord;
import io.github.architers.server.file.enums.TaskStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 导出
 *
 * @author luyi
 */
@RestController
@RequestMapping("/export")
@Slf4j
public class ExportController {


    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Resource
    private StreamBridge streamBridge;

    @PostMapping("/exportFile")
    public void exportFile(@RequestBody @Validated FileTaskParam<ExportEntity> fileTaskParam) {
        executorService.submit(() -> {
            TaskRecord taskRecord = new TaskRecord().setId(fileTaskParam.getRecordId());
            try {
                Thread.sleep(5000);
                taskRecord.setStatus(TaskStatusEnum.FINISHED)
                        .setTotalNum(1000).setSuccessNum(500);
            } catch (Exception e) {
                log.error("处理任务失败", e);
                taskRecord.setStatus(TaskStatusEnum.FAIL);
            }
            streamBridge.send("file-task-result", taskRecord);
        });
    }


}
