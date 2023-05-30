package io.github.architers.server.file.mq.consumer;


import io.github.architers.context.exception.BusException;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.architers.server.file.domain.params.ImportTaskParam;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import io.github.architers.server.file.service.IFileTaskImportRecordService;
import io.github.architers.server.file.service.IFileTaskService;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RocketMQMessageListener(topic = "file_import_task", consumerGroup = "file_import_task_consumer",
        consumeThreadNumber = 10, maxReconsumeTimes = 3)
public class FileImportMessageConsumer implements RocketMQListener<MessageExt> {


    @Resource
    private IFileTaskService fileTaskService;

    @Resource
    private IFileTaskImportRecordService fileTaskImportRecordService;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void onMessage(MessageExt messageExt) {
        FileTaskImportRecord importRecord = JsonUtils.readValue(messageExt.getBody(), FileTaskImportRecord.class);
        if (messageExt.getReconsumeTimes() >= 3) {
            //三次就导出失败
            FileTaskImportRecord fileTaskImportRecord = new FileTaskImportRecord();
            fileTaskImportRecord.setStatus(TaskRecordStatusEnum.CANCEL.getStatus());
            fileTaskImportRecord.setRequestId(importRecord.getRequestId());
            fileTaskImportRecord.setRemark("导出重试达到指定次数，任务取消");
            fileTaskImportRecordService.updateByRequestId(fileTaskImportRecord);
            return;
        }
        FileTask fileTask = fileTaskService.findByTaskCode(importRecord.getTaskCode());
        ImportTaskParam<Map<String, Object>> importTaskParam = new ImportTaskParam<>();
        importTaskParam.setSourceUrl(importRecord.getSourceUrl());
        importTaskParam.setExportUserId(importRecord.getCreateBy());
        importTaskParam.setTaskCode(importRecord.getTaskCode());
        importTaskParam.setRequestId(importRecord.getRequestId());
        importTaskParam.setRequestBody(importRecord.getRequestBody());
        //请求头
        HttpHeaders headers = new HttpHeaders();
        //封装成一个请求对象
        HttpEntity<String> entity = new HttpEntity<>(JsonUtils.toJsonString(importTaskParam), headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //地址远程调用
        ResponseResult<?> responseResult = restTemplate.postForEntity(fileTask.getTaskAddress(), entity, ResponseResult.class).getBody();

        assert responseResult != null;
        if (HttpStatus.OK.value() != responseResult.getCode()) {
            throw new BusException("转发导入任务失败");
        }

    }
}
