package io.github.architers.objectstorage.cos;

import io.github.architers.objectstorage.CommonProperties;
import lombok.Data;

/**
 * minio属性配置
 *
 * @author luyi
 */
@Data
public class CosProperties extends CommonProperties {
    private String region;

}
