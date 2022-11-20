package io.github.architers.server.file.api;

import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/taskApi")
public class TaskApi {

    @Resource
    private TaskService taskService;

    /**
     * 添加任务
     */
    @PostMapping("/executeTask")
    public void executeTask(@RequestBody @Validated ExecuteTaskParam executeTaskParam) {
        taskService.sendTask(executeTaskParam);
    }


}
