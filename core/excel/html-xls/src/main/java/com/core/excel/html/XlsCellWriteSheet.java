package com.core.excel.html;


import com.core.excel.html.emun.CellAlign;
import com.core.excel.html.emun.CellBorder;
import com.core.excel.html.entity.XlsCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：将XlsCell数据转写到excel
 *
 * @author luyi
 * @date 2021/3/17
 */
@Component
public class XlsCellWriteSheet {

    public void write(List<List<XlsCell>> rowCells, XSSFSheet sheet) {
        for (List<XlsCell> rowCell : rowCells) {
            XSSFWorkbook workbook = sheet.getWorkbook();
            rowCell.forEach(xlsCell -> {
                int startRow = xlsCell.getStartRow();
                int endRow = xlsCell.getEndRow();
                int startCol = xlsCell.getStartCol();
                int endCol = xlsCell.getEndCol();
                for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        //行不存在就创建行
                        row = sheet.createRow(rowNum);
                    }
                    //样式
                    Map<String, CellStyle> styleMap = new HashMap<>();
                    for (int colIndex = startCol; colIndex <= endCol; colIndex++) {
                        Cell cell = row.createCell(colIndex);
                        cell.setCellValue(xlsCell.getText());
                        this.setCellStyle(cell, styleMap, xlsCell, workbook);
                    }
                }
                //合并单元格
                if (xlsCell.getColspan() > 1 || xlsCell.getRowspan() > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(xlsCell.getStartRow(), xlsCell.getEndRow(), xlsCell.getStartCol(), xlsCell.getEndCol()));
                }
            });
        }

    }

    /**
     * 描述：设置样式
     * 将样式属性字段拼成key
     *
     * @author luyi
     * @date 2021/3/17
     */
    private void setCellStyle(Cell cell, Map<String, CellStyle> styleMap, XlsCell xlsCell, XSSFWorkbook workbook) {
        StringBuilder styleKey = new StringBuilder("");
        styleKey.append(xlsCell.getCellBorder() == null ? "" : xlsCell.getCellBorder().toString());
        styleKey.append("_");
        styleKey.append(xlsCell.getValign().toString());
        styleKey.append("_");
        CellAlign.LevelAlign levelAlign = xlsCell.getLevelAlign();
        styleKey.append(levelAlign == null ? "" : levelAlign.toString());
        CellStyle cellStyle = styleMap.get(styleKey.toString());
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            this.setBorderCellStyle(xlsCell, cellStyle);
            this.setAlignCellStyle(xlsCell, cellStyle);
            styleMap.put(styleKey.toString(), cellStyle);
        }
        cell.setCellStyle(cellStyle);
    }

    /**
     * 描述：设置对齐样式
     *
     * @author luyi
     * @date 2021/3/17
     */
    private void setAlignCellStyle(XlsCell xlsCell, CellStyle cellStyle) {
        //水平样式
        if (xlsCell.getLevelAlign() != null) {
            switch (xlsCell.getLevelAlign()) {
                case CellAlign.LevelAlign.center:
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    break;
                case CellAlign.LevelAlign.left:
                    cellStyle.setAlignment(HorizontalAlignment.LEFT);
                    break;
                case CellAlign.LevelAlign.right:
                    cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                    break;
                default:
                    break;
            }
        }
        //垂直样式
        if (xlsCell.getValign() != null) {
            switch (xlsCell.getValign()) {
                case CellAlign.Valign.center:
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    break;
                case CellAlign.Valign.top:
                    cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                    break;
                case CellAlign.Valign.bottom:
                    cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 描述：设置边框样式
     *
     * @author luyi
     * @date 2021/3/17
     */
    private void setBorderCellStyle(XlsCell xlsCell, CellStyle cellStyle) {
        CellBorder cellBorder = xlsCell.getCellBorder();
        if (cellBorder == null) {
            return;
        }
        switch (cellBorder) {
            case ALL:
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                break;
            case TOP:
                cellStyle.setBorderTop(BorderStyle.THIN);
                break;
            case BOTTOM:
                cellStyle.setBorderBottom(BorderStyle.THIN);
                break;
            case LEFT:
                cellStyle.setBorderLeft(BorderStyle.THIN);
                break;
            case RIGHT:
                cellStyle.setBorderRight(BorderStyle.THIN);
                break;
            default:
                break;
        }
    }
}
