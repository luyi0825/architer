package io.github.architers.excel.html;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import io.github.architers.excel.html.entity.XlsCell;


/**
 * @author luyi
 */
public class XlsCellConverter implements Converter<XlsCell> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return XlsCell.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public XlsCell convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return null;
    }

    @Override
    public CellData<XlsCell> convertToExcelData(XlsCell value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        CellData<XlsCell> cellData = new CellData<>();
        cellData.setType(CellDataTypeEnum.STRING);
        cellData.setStringValue(value.getText());
        cellData.setRowIndex(value.getStartRow());
        cellData.setColumnIndex(value.getStartCol());
        cellData.setData(value);
        return cellData;
    }
}
