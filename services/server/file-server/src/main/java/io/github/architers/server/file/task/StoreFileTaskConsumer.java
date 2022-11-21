package io.github.architers.server.file.task;

import io.github.architers.context.web.ResponseResult;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.service.IFileTaskService;
import io.github.architers.server.file.service.ITaskRecordService;
import io.github.architers.server.file.service.impl.FileFileTaskServiceImpl;
import lombok.SneakyThrows;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

/**
 * @author luyi
 */
@Component()
@MessageMapping(value = "fileTaskConsumer")
public class StoreFileTaskConsumer implements Consumer<ExecuteTaskParam> {

    @Resource
    private IFileTaskService fileTaskService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StreamBridge streamBridge;

    @Resource
    private ITaskRecordService taskRecordService;

    @SneakyThrows
    @Override
    public void accept(ExecuteTaskParam param) {
        Long taskId = taskRecordService.initTask(param.getTaskCode());
        FileTask fileTask = fileTaskService.findByTaskCode(param.getTaskCode());
        String taskAddress = fileTask.getTaskAddress();
        ResponseResult<?> responseResult = null;
        if (taskAddress.startsWith("lb://")) {
            int pathIndex = taskAddress.indexOf("/", 6);
            String host = taskAddress.substring(5, pathIndex);
            String path = taskAddress.substring(pathIndex);
            URI url = new URI("http", host, path, null);
            responseResult = restTemplate.postForEntity(url, param, ResponseResult.class).getBody();
        } else if (taskAddress.startsWith("stream://")) {
            String target = taskAddress.substring(9);
            streamBridge.send(target, param);
        } else {
            responseResult = restTemplate.postForEntity(fileTask.getTaskAddress(),
                    param, ResponseResult.class).getBody();
        }

        if (responseResult != null) {
            if (HttpStatus.OK.value() == responseResult.getCode()) {
                System.out.println("处理成功");
            }
        }

    }
}
