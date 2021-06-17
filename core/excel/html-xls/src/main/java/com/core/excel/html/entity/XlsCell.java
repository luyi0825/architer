
package com.core.excel.html.entity;


import com.core.excel.html.emun.CellAlign;
import com.core.excel.html.emun.CellBorder;
import lombok.Data;

/**
 * cell信息
 *
 * @author luyi
 */
@Data
public class XlsCell {
    /**
     * 文本
     */
    private String text;
    /**
     * 开始行
     */
    private int startRow;
    /**
     * 结束行
     */
    private int endRow;
    /**
     * 开始列
     */
    private int startCol;
    /**
     * 结束列
     */
    private int endCol;

    /**
     * 边框
     */
    private CellBorder cellBorder;
    /**
     * 水平对齐方式
     */
    private CellAlign.LevelAlign levelAlign;
    /**
     * 垂直对齐方式
     */
    private CellAlign.Valign valign = CellAlign.Valign.center;
    /**
     * 行数
     */
    private int rowspan;
    /**
     * 列数
     */
    private int colspan;


    @Override
    public String toString() {
        return "XlsCell{" +
                "text='" + text + '\'' +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", startCol=" + startCol +
                ", endCol=" + endCol +
                ", cellBorder=" + cellBorder +
                ", levelAlign=" + levelAlign +
                ", valign=" + valign +
                ", rowspan=" + rowspan +
                ", colspan=" + colspan +
                '}';
    }
}
