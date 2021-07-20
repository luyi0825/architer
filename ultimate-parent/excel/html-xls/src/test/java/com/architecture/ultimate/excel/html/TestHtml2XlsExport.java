package com.architecture.ultimate.excel.html;


import com.architecture.ultimate.excel.html.emun.HtmlType;
import com.architecture.ultimate.excel.html.entity.HtmlSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHtml2XlsExport {
    @Value("classpath:html2xls001.html")
    private Resource resource001;

    @Value("classpath:html2xls002.html")
    private Resource resource002;

    @Autowired
    private Html2XlsExport xlsExport;

    @Test
    public void testExport() throws IOException {
        Resource resource = resource002;
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlSheet htmlSheet = new HtmlSheet();
        htmlSheet.setHtml(result.toString());
        htmlSheet.setHtmlType(HtmlType.DEFAULT.getType());
        String resourcePath = resource.getFile().getAbsolutePath();
        String excelPath = resourcePath.substring(0, resourcePath.lastIndexOf(".")) + ".xls";
        File file = new File(excelPath);
        OutputStream outputStream = new FileOutputStream(file);
        xlsExport.export(htmlSheet, outputStream);
        outputStream.close();
        System.out.println("yes");
    }
}
