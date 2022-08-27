package com.test.objectstorage.cos;

import com.test.objectstorage.CommonProperties;
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
