package io.github.architers.server.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.server.file.domain.entity.FileTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author luyi
 */
public interface FileTemplateMapper extends BaseMapper<FileTemplate> {
    FileTemplate selectByTemplateCode(@Param("templateCode") String templateCode);
}
