package com.architecture.excel.html.emun;
/**
 * 描述：cell对齐方式
 *
 * @author luyi
 * @date 2021/3/30
 */
public class CellAlign {
    /**
     * 水平对齐
     */
    public enum LevelAlign {
        /**
         * 居中
         */
        CENTER(),
        /**
         * 左对齐
         */
        LEFT(),
        /**
         * 右对齐
         */
        RIGHT()
    }
    /**
    *垂直
     */
    public enum Valign {
        /**
         * 垂直居中
         */
        MIDDLE(),
        /**
         * 上对齐
         */
        TOP(),
        /**
         * 下对齐
         */
        BOTTOM()
    }

}
