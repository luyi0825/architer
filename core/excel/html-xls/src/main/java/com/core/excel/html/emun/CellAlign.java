package com.core.excel.html.emun;

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
        center(),
        /**
         * 左对齐
         */
        left(),
        /**
         * 右对齐
         */
        right()
    }

    public enum Valign {
        /**
         * 居中
         */
        center(),
        /**
         * 上对齐
         */
        top(),
        /**
         * 下对齐
         */
        bottom()
    }

}
