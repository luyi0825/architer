package com.business.message.captcha.api;

import com.business.message.captcha.entity.CaptchaConfig;
import com.core.module.common.valid.group.AddGroup;
import com.core.mybatisplus.Pagination;
import com.core.mybatisplus.QueryParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

/**
 * 验证码接口层
 *
 * @author luyi
 */
@RestController("captchaConfigApi")
@Api(tags = "验证码配置")
public interface CaptchaConfigApi {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return 分页信息
     */
    @ApiOperation(value = "分页查询")
    @ApiOperationSupport(ignoreParameters = {"entity"})
    @PostMapping("/pageQuery")
    Pagination pageQuery(@RequestBody  QueryParam<CaptchaConfig> queryParam);

    /**
     * 添加验证码配置
     *
     * @param captchaConfig 配置信息
     */
    @ApiOperation(value = "添加验证码配置")
    @PostMapping("/add")
    void addCaptchaConfig(@RequestBody @Validated(AddGroup.class) CaptchaConfig captchaConfig);
}
