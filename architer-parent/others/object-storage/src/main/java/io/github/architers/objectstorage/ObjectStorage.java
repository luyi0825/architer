package io.github.architers.objectstorage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 对象存储
 *
 * @author luyi
 */
public interface ObjectStorage {

    /**
     * 上传对象
     *
     * @param file 需要上传的对象
     * @param key  对象的标识key
     * @return 上传的结果
     */
    PutFileResponse putObject(File file, String key);

    PutFileResponse putObject(InputStream inputStream, String key);

    void getObject(OutputStream outputStream, String key) throws Exception;

    /**
     * 删除文件
     *
     * @param key 需要删除的文件
     */
    boolean deleteObject(String key);

}
