package io.github.architers.server.file.service;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 导入模板文件
 *
 * @author luyi
 */
public interface ImportTemplateFileService {

    void importTemplateFile(File file, String templateCode) throws FileNotFoundException;
}
