package io.github.architers.server.file.api;

import cn.hutool.extra.qrcode.QrCodeUtil;
import io.github.architers.context.web.IgnoreResponseResult;
import io.github.architers.server.file.service.TestService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Administrator
 */
@RestController
public class TestApi {

    @GetMapping("qrCode")
    @IgnoreResponseResult
    public void QrCode(HttpServletResponse response) throws IOException {
        String url = "https://www.rr3025.net/type/24-2.html";
        // 生成二维码并指定宽高
        BufferedImage generate = QrCodeUtil.generate(url, 300, 300);
        // 转换流信息写出
        ImageIO.write(generate, "jpg", response.getOutputStream());
    }

    @Resource
    private TestService testService;

    @GetMapping("/testAsync")
    public void testAsync() {
        testService.test();
    }

}
