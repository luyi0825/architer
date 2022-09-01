package io.github.architers.excel.html.entity;

import lombok.Data;

import java.util.List;

/**
 * @author luyi
 * xls的sheet信息
 */
@Data
public class XlsSheet {

    /**
     * Cell集合
     */
    private List<List<XlsCell>> cellList;

    /**
     * 合并的cell信息
     */
    private List<XlsCell> mergeCells;

    public XlsSheet(List<List<XlsCell>> cellList, List<XlsCell> mergeCells) {
        this.cellList = cellList;
        this.mergeCells = mergeCells;
    }
}
