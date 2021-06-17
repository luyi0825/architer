package com.core.captcha;


import com.core.captcha.base.CharacterCaptcha;
import com.core.captcha.model.CaptchaModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 字符验证码
 *
 * @author luyi
 */
public class CharacterStaticCaptcha extends CharacterCaptcha {

    @Override
    public String toBase64() {
        return toBase64("data:image/png;base64,");
    }

    /**
     * 生成验证码图形
     *
     * @param code 验证码
     * @param out  输出流
     */
    @Override
    public void graphicsImage(OutputStream out, String code, CaptchaModel model) throws IOException {
        int width = model.getWidth(), height = model.getHeight();
        try {
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            // 填充背景
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 画干扰圆
            drawOval(2, g2d, model);
            // 画干扰线
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            //画贝塞尔曲线
            drawBesselLine(1, g2d, model);
            // 画字符串
            g2d.setFont(getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            // 每一个字符所占的宽度
            int fW = width / code.length();
            // 字符的左右边距
            int fSp = (fW - (int) fontMetrics.getStringBounds("W", g2d).getWidth()) / 2;
            for (int i = 0; i < code.length(); i++) {
                //字符常用，放入放入字符串常量池
                String charCode = (code.charAt(i) + "").intern();
                g2d.setColor(color());
                // 文字的纵坐标
                int fY = height - ((height - (int) fontMetrics.getStringBounds(charCode, g2d).getHeight()) >> 1);
                g2d.drawString(charCode, i * fW + fSp + 3, fY - 3);
            }
            g2d.dispose();
            ImageIO.write(bufferedImage, "png", out);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}