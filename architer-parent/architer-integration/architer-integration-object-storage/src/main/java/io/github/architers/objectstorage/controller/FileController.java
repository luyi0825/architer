//package io.github.architers.objectstorage.controller;
//
//import io.github.architers.objectstorage.ObjectStorageType;
//import io.github.architers.objectstorage.PutFileResponse;
//import io.github.architers.objectstorage.ObjectStorage;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/file")
//public class FileController {
//
//    @Resource(name = ObjectStorageType.MINIO)
//    private ObjectStorage objectStorage;
//
//    /**
//     * 上传文件
//     */
//    @PostMapping("/uploadFile")
//    public PutFileResponse uploadFile(MultipartFile file) throws IOException {
//        String key = file.getOriginalFilename();
//        return objectStorage.putObject(file.getInputStream(), key);
//    }
//
//    /**
//     * 下载文件
//     */
//    @GetMapping("/downloadFile")
//    public void downloadFile(HttpServletResponse response, String key) throws Exception {
//
//        objectStorage.getObject(response.getOutputStream(),  key);
//    }
//
//    /**
//     * 删除文件
//     */
//    @DeleteMapping("/deleteFile")
//    public boolean deleteFile(@RequestParam("fileName") String fileName) {
//        return objectStorage.deleteObject(fileName);
//    }
//}
