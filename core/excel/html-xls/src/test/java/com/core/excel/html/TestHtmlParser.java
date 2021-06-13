package com.core.excel.html;

import com.core.excel.html.emun.HtmlType;
import com.core.excel.html.entity.HtmlSheet;
import com.core.excel.html.entity.XlsSheet;
import com.core.excel.html.parser.HtmlParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHtmlParser {

    @Value("classpath:html2xls001.html")
    private Resource resource001;


    @Autowired
    private HtmlParser htmlParser;

    /**
     * 测试解析方法
     *
     */
    @Test
    public void testParse() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(resource001.getFile()));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlSheet htmlSheet = new HtmlSheet();
        htmlSheet.setHtml(result.toString());
        htmlSheet.setHtmlType(HtmlType.DEFAULT.getType());
        XlsSheet sheet = htmlParser.parse(htmlSheet);
        System.out.println(sheet);
    }
}
