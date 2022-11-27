package io.github.architers.server.file.task;

import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.domain.dto.FileTaskParam;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.domain.entity.FileTaskRecord;
import io.github.architers.server.file.enums.TaskStatusEnum;
import io.github.architers.server.file.service.IFileTaskService;
import io.github.architers.server.file.service.ITaskRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author luyi
 */
@Component
@Slf4j
public class StoreFileTaskConsumer {


    @Bean
    public Consumer<ExecuteTaskParam> storeFileTask() {
        return this::acceptTask;
    }

    @Resource
    private IFileTaskService fileTaskService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StreamBridge streamBridge;

    @Resource
    private ITaskRecordService taskRecordService;


    public void acceptTask(ExecuteTaskParam param) {
        //初始化任务
        Long taskId = taskRecordService.initTask(param.getTaskCode());
        FileTask fileTask = fileTaskService.findByTaskCode(param.getTaskCode());
        String taskAddress = fileTask.getTaskAddress();
        ResponseResult<?> responseResult = null;
        try {
            if (taskAddress.startsWith("stream://")) {
                //发送到mq中
                String target = taskAddress.substring(9);
                streamBridge.send(target, param);
            } else {
                //请求头
                HttpHeaders headers = new HttpHeaders();
                FileTaskParam<Map<String, Object>> fileTaskParam = new FileTaskParam<Map<String, Object>>()
                        .setRecordId(taskId)
                        .setTaskParam(param.getExecuteParam())
                        .setTaskCode(param.getTaskCode());
                //封装成一个请求对象
                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(JsonUtils.toMap(fileTaskParam),
                        headers);
                headers.setContentType(MediaType.APPLICATION_JSON);
                //地址远程调用
                responseResult = restTemplate.postForEntity(fileTask.getTaskAddress(),
                        entity, ResponseResult.class).getBody();
            }
        } catch (Exception e) {
            log.error("远程调用失败", e);
            //说明直接就远程调用失败
            FileTaskRecord fileTaskRecord = new FileTaskRecord();
            fileTaskRecord.setId(taskId);
            fileTaskRecord.setRemark(e.getMessage());
            fileTaskRecord.setStatus(TaskStatusEnum.FAIL.getStatus());
            taskRecordService.updateById(fileTaskRecord);
            return;
        }
        if (HttpStatus.OK.value() == responseResult.getCode()) {
            FileTaskRecord fileTaskRecord = new FileTaskRecord();
            fileTaskRecord.setId(taskId);
            fileTaskRecord.setStatus(TaskStatusEnum.PROCESSING.getStatus());
            taskRecordService.updateById(fileTaskRecord);
        } else {
            FileTaskRecord fileTaskRecord = new FileTaskRecord();
            fileTaskRecord.setId(taskId);
            fileTaskRecord.setRemark(responseResult.getMessage());
            fileTaskRecord.setStatus(TaskStatusEnum.FAIL.getStatus());
            taskRecordService.updateById(fileTaskRecord);
        }
    }
}
