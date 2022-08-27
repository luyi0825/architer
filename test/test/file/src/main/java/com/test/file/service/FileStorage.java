package com.test.file.service;

import com.test.file.PutFileResponse;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件存储
 *
 * @author luyi
 */
public interface FileStorage {

    /**
     * 上传文件
     *
     * @param file 需要上传的文件
     * @param key  文件的标识key
     * @return 上传的结果
     */
    PutFileResponse putFile(File file, String key);

    PutFileResponse uploadFile(InputStream inputStream, String key);

    void downloadFile(OutputStream outputStream, String key) throws Exception;

    /**
     * 删除文件
     *
     * @param key 需要删除的文件
     */
    boolean delete(String key);

}
