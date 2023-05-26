package io.github.architers.server.file.utils;

import io.github.architers.context.exception.BusLogException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ExcelVersionUtils {

    private static final String TEMPLATE_VERSION = "template_version";


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
            CTProperty ctProperty = poixmlProperties.getCustomProperties().getProperty(TEMPLATE_VERSION);
            if (ctProperty == null) {
                return null;
            }
            return ctProperty.getLpwstr();
        } catch (Exception e) {
            throw new BusLogException("检查模板异常", e);
        }
    }


    public static File fillFileVersion(InputStream inputStream, String version) {
        Assert.notNull(version, "版本不能为空");
        File tempFile;
        OPCPackage pkg = null;

        try {
            //用OPCPackage填充版本号，防止oom
            pkg = OPCPackage.open(inputStream);
            POIXMLProperties poixmlProperties = new POIXMLProperties(pkg);
            POIXMLProperties.CustomProperties customProperties = poixmlProperties.getCustomProperties();
            CTProperty ctProperty = customProperties.getProperty(TEMPLATE_VERSION);
            if (ctProperty != null) {
                ctProperty.setLpwstr(version);
            } else {
                customProperties.addProperty(TEMPLATE_VERSION, version);
            }
            //提交属性值
            poixmlProperties.commit();
            pkg.flush();
            tempFile = TempFileUtil.generateXlsxTempFile();
            pkg.save(tempFile);

            return tempFile;
        } catch (Exception e) {
            log.error("填充模板文件模板失败", e);
            //异常转换
            throw new RuntimeException("填充模板文件模板失败", e);
        } finally {
            IOUtils.closeQuietly(pkg);
            IOUtils.closeQuietly(inputStream);
        }
    }

}
