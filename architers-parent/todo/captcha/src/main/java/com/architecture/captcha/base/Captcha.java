package io.github.architers.captcha.base;

import io.github.architers.captcha.model.CaptchaModel;
import io.github.architers.captcha.CaptchaConstants;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 验证码抽象类
 *
 * @author luyi
 */
public abstract class Captcha {
    /**
     * 常用颜色
     */
    public static final int[][] COLOR = {{0, 135, 255}, {51, 153, 51}, {255, 102, 102}, {255, 153, 0}, {153, 102, 0}, {153, 102, 153}, {51, 153, 153}, {102, 102, 255}, {0, 102, 204}, {204, 51, 51}, {0, 153, 204}, {0, 51, 102}};
    /**
     * 内置字体
     */
    private static final String[] FONT_NAMES = new String[]{"actionj.ttf", "epilog.ttf", "fresnel.ttf", "headache.ttf", "lexo.ttf", "prefix.ttf", "progbot.ttf", "ransom.ttf", "robot.ttf", "scandal.ttf"};
    /**
     * 验证码的字体
     */
    private Font font = new Font("Arial", Font.BOLD, 32);

    private final CaptchaModel defaultCaptchaModel;

    {
        defaultCaptchaModel = new CaptchaModel();
        defaultCaptchaModel.setFont(font);
        defaultCaptchaModel.setWidth(130);
        defaultCaptchaModel.setHeight(48);
        defaultCaptchaModel.setLen(4);
    }

    /**
     * 得到默认的验证码模型
     */
    private CaptchaModel getDefaultCaptchaModel() {
        return this.defaultCaptchaModel;
    }


    /**
     * 验证码输出,抽象方法，由子类实现
     *
     * @param os 输出流
     * @return 验证码结果
     * @throws IOException 图片验证码IO异常
     */
    public String out(OutputStream os, CaptchaModel model) throws IOException {
        if (model == null) {
            model = this.getDefaultCaptchaModel();
        }
        String code = generateCode(model.getLen());
        this.graphicsImage(os, code, model);
        return code;
    }

    /**
     * 画验证码
     *
     * @param code 验证码
     * @param os   输出流
     */
    protected abstract void graphicsImage(OutputStream os, String code, CaptchaModel model) throws IOException;

    /**
     * 生成验证码
     *
     * @param len 验证码长度
     * @return 生成的验证码
     */
    protected abstract String generateCode(int len);

    /**
     * 输出base64编码
     *
     * @return base64编码字符串
     */
    public abstract String toBase64();

    /**
     * 输出base64编码
     *
     * @param type 编码头
     * @return base64编码字符串
     */
    public String toBase64(String type) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        return type + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }


    /**
     * 随机画干扰线
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public void drawLine(int num, Graphics2D g, CaptchaModel model) {
        drawLine(num, null, g, model);
    }

    /**
     * 随机画干扰线
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public void drawLine(int num, Color color, Graphics2D g, CaptchaModel model) {
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int x1 = num(-10, model.getWidth() - 10);
            int y1 = num(5, model.getHeight() - 5);
            int x2 = num(10, model.getWidth() + 10);
            int y2 = num(2, model.getHeight() - 2);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 给定范围获得随机颜色
     *
     * @param fc 0-255
     * @param bc 0-255
     * @return 随机颜色
     */
    protected Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取随机常用颜色
     *
     * @return 随机颜色
     */
    protected Color color() {
        int[] color = COLOR[num(COLOR.length)];
        return new Color(color[0], color[1], color[2]);
    }


    /**
     * 随机画干扰圆
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public void drawOval(int num, Graphics2D g, CaptchaModel model) {
        drawOval(num, null, g, model);
    }

    /**
     * 随机画干扰圆
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public void drawOval(int num, Color color, Graphics2D g, CaptchaModel model) {
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int w = 5 + num(10);
            g.drawOval(num(model.getWidth() - 25), num(model.getHeight() - 15), w, w);
        }
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int num(int min, int max) {
        return min + CaptchaConstants.RANDOM.nextInt(max - min);
    }

    /**
     * 产生0-num的随机数,不包括num
     *
     * @param num 最大值
     * @return 随机数
     */
    public static int num(int num) {
        return CaptchaConstants.RANDOM.nextInt(num);
    }

    /**
     * 随机画贝塞尔曲线
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    public void drawBesselLine(int num, Graphics2D g, CaptchaModel model) {
        drawBesselLine(num, null, g, model);
    }

    /**
     * 随机画贝塞尔曲线
     *
     * @param num   数量
     * @param color 颜色
     * @param g     Graphics2D
     */
    public void drawBesselLine(int num, Color color, Graphics2D g, CaptchaModel model) {
        int width = model.getWidth(), height = model.getHeight();
        for (int i = 0; i < num; i++) {
            g.setColor(color == null ? color() : color);
            int x1 = 5, y1 = num(5, height / 2);
            int x2 = model.getWidth() - 5,
                    y2 = num(height / 2, height - 5);
            int ctrlx = num(width / 4, width / 4 * 3), ctrly = num(5, height - 5);
            if (num(2) == 0) {
                int ty = y1;
                y1 = y2;
                y2 = ty;
            }
            if (num(2) == 0) {  // 二阶贝塞尔曲线
                QuadCurve2D shape = new QuadCurve2D.Double();
                shape.setCurve(x1, y1, ctrlx, ctrly, x2, y2);
                g.draw(shape);
            } else {  // 三阶贝塞尔曲线
                int ctrlx1 = num(width / 4, width / 4 * 3), ctrly1 = num(5, height - 5);
                CubicCurve2D shape = new CubicCurve2D.Double(x1, y1, ctrlx, ctrly, ctrlx1, ctrly1, x2, y2);
                g.draw(shape);
            }
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setFont(int font) throws IOException, FontFormatException {
        setFont(font, 32f);
    }

    public void setFont(int font, float size) throws IOException, FontFormatException {
        setFont(font, Font.BOLD, size);
    }

    public void setFont(int font, int style, float size) throws IOException, FontFormatException {
        this.font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/" + FONT_NAMES[font])).deriveFont(style, size);
    }


}