package io.github.architers.server.file.domain.param;

import cn.hutool.core.lang.Assert;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import io.github.architers.server.file.domain.entity.FileTemplateCheckRowInfo;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 文件模板校验行信息
 *
 * @author luyi
 */
@Data
public class FileTemplateCheckRowInfoParams {
    /**
     * 是否开启行校验
     */
    @NotNull(message = "是否开启行校验不能为空", groups = {AddGroup.class, EditGroup.class})
    private Boolean enableCheck;
    /**
     * 校验开始行
     */
    private Integer startRow;
    /**
     * 检验结束行
     */
    private Integer endRow;

    /**
     * 校验数据合法性
     */
    public void checkDataLegality() {
        if (Boolean.TRUE.equals(enableCheck)) {
            Assert.notNull(startRow, "开始行不能为空");
            Assert.notNull(endRow, "结束行不能为空");
            if (startRow > endRow) {
                throw new BusException("结束行不能小于开始行");
            }
            if (endRow - startRow > 20) {
                throw new BusException("校验行数不能超过20");
            }
        }


    }

    /**
     * 是否需要刷新Base64RowStr
     *
     * @param rowInfo
     * @return
     */
    public boolean needRefreshBase64RowStr(FileTemplateCheckRowInfo rowInfo) {
        if (!Boolean.TRUE.equals(enableCheck)) {
            return false;
        }
        if (Objects.equals(startRow, rowInfo.getStartRow()) && Objects.equals(endRow, rowInfo.getEndRow())) {
            return false;
        }
        return true;
    }
}
