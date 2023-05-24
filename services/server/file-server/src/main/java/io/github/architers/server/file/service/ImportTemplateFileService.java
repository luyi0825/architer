package io.github.architers.server.file.service;

import java.io.File;

/**
 * 导入模板文件
 *
 * @author luyi
 */
public interface ImportTemplateFileService {

    void importTemplateFile(File file, String templateCode);
}
