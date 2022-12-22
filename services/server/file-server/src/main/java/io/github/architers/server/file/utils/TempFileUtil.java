package io.github.architers.server.file.utils;

import io.github.architers.context.exception.SysException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;

/**
 * 临时文件工具类
 *
 * @author luyi
 */
@Slf4j
public class TempFileUtil {

    private TempFileUtil() {

    }

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir") + "tmpdir";

    static {

        File file = new File(TMP_DIR);
        //不存在就生成临时文件
        if (!file.exists() && !file.mkdirs()) {
            throw new SysException("生成临时文件目录失败");
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
