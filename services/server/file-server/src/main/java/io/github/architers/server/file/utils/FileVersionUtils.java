package io.github.architers.server.file.utils;

import io.github.architers.context.exception.NoStackBusException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.springframework.util.Assert;

import java.io.*;

/**
 * 文件版本工具类
 *
 * @author luyi
 */
public class FileVersionUtils {

    private static final String CUSTOM_FILE_VERSION = "custom_version";


    public static File fillFileVersion(File file, String version) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return fillFileVersion(inputStream, version);
        } catch (IOException e) {
            //异常转换
            throw new RuntimeException("填充模板文件模板失败", e);
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * 得到模板版本
     *
     * @param inputStream 文件输入流
     * @return 模板版本
     */
    public static String getFileVersion(InputStream inputStream) {
        try (OPCPackage pkg = OPCPackage.open(inputStream)) {
            POIXMLProperties poixmlProperties = new POIXMLProperties(pkg);
            CTProperty ctProperty = poixmlProperties.getCustomProperties().getProperty(CUSTOM_FILE_VERSION);
            if (ctProperty == null) {
                return null;
            }
            return ctProperty.getLpwstr();
        } catch (Exception e) {
            throw new NoStackBusException("检查模板异常", e);
        }
    }


    public static File fillFileVersion(InputStream inputStream, String version) {
        Assert.notNull(version, "版本不能为空");
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
            POIXMLProperties.CustomProperties customProperties = xssfWorkbook.getProperties().getCustomProperties();
            CTProperty ctProperty = customProperties.getProperty(CUSTOM_FILE_VERSION);
            if (ctProperty != null) {
                ctProperty.setLpwstr(version);
            } else {
                customProperties.addProperty(CUSTOM_FILE_VERSION, version);
            }
            File file = TempFileUtil.generateXlsxTempFile();
            FileOutputStream fos = new FileOutputStream(file);
            xssfWorkbook.write(fos);
            return file;
        } catch (IOException e) {
            //异常转换
            throw new RuntimeException("填充模板文件模板失败", e);
        } finally {
            IOUtils.closeQuietly(xssfWorkbook);
            IOUtils.closeQuietly(inputStream);
        }
    }

}
