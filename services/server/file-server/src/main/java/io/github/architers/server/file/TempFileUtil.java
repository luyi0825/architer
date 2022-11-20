package io.github.architers.server.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;

/**
 * @author luyi
 */
@Slf4j
public class TempFileUtil {

    public static String TMP_DIR = System.getProperty("java.io.tmpdir") + "tmpdir";

    static {
        File file = new File(TMP_DIR);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("生成临时文件目录失败");
            }
        }
        log.info("临时文件目录:" + TMP_DIR);
    }

    /**
     * 生成xlsx临时文件
     * <li>记得删除</li>
     *
     * @return 生成的临时文件
     */
    public static File generateXlsxTempFile() {
        return new File(TMP_DIR + File.separator + UUID.randomUUID() + ".XLSX");
    }

}
