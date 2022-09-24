package io.github.architers.test.asynctask.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author luyi
 * 异步任务
 */
@RestController
@RequestMapping("/asyncTask")
public class AsyncTaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/add")
    public void add() throws InterruptedException {
        taskService.addAsyncTask(UUID.randomUUID().toString(), "test");
    }

    @PostMapping("/taskSend")
    public void taskSend(){
        taskService.sendTask();
    }


}
