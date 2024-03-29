package io.github.arthiters.test.file.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.architers.server.file.domain.params.ImportTaskParam;
import io.github.arthiters.test.file.domain.params.ExportUser;
import io.github.arthiters.test.file.service.IExportImportService;
import io.github.arthiters.test.file.service.IExportUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class FileTaskController {

    @Resource
    private IExportUserService exportUserService;

    @Resource
    private IExportImportService importService;

    @PostMapping("/exportUser")
    public void exportUse(@RequestBody ExportTaskParam<ExportUser> exportTaskParam) {
      //  System.out.println(JsonUtils.toJsonString(objectMap));
//        ExportTaskRequest<ExportUser> exportTaskRequest = JSON.parseObject(JsonUtils.toJsonString(objectMap), new TypeReference<ExportTaskRequest<ExportUser>>() {
//        });
//        System.out.println(exportTaskRequest);
        exportUserService.exportUse(exportTaskParam);
    }

    @PostMapping("/importData")
    public void importData(@RequestBody ImportTaskParam<Map<String,Object>> importTaskParam) {
        //  System.out.println(JsonUtils.toJsonString(objectMap));
//        ExportTaskRequest<ExportUser> exportTaskRequest = JSON.parseObject(JsonUtils.toJsonString(objectMap), new TypeReference<ExportTaskRequest<ExportUser>>() {
//        });
//        System.out.println(exportTaskRequest);
        importService.importData(importTaskParam);
    }

    public static void main(String[] args) {
        String str = "{\"exportUserId\":1,\"requestBody\":\"{\\\"userName\\\":\\\"1\\\"}\"}";

        ExportTaskParam<ExportUser> exportTaskParam = JSON.parseObject(str, new TypeReference<ExportTaskParam<ExportUser>>() {
        });
        System.out.println(exportTaskParam);
    }

}
