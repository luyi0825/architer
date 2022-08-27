package com.test.file.controller;

import com.test.file.PutFileResponse;
import com.test.file.service.FileStorage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileStorage fileStorage;

    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    public PutFileResponse uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();
        return fileStorage.uploadFile(file.getInputStream(), key);
    }

    /**
     * 下载文件
     */
    @GetMapping("/downloadFile")
    public void downloadFile(HttpServletResponse response, String key) throws Exception {

        fileStorage.downloadFile(response.getOutputStream(),  key);
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/deleteFile")
    public boolean deleteFile(@RequestParam("fileName") String fileName) {
        return fileStorage.delete(fileName);
    }
}
