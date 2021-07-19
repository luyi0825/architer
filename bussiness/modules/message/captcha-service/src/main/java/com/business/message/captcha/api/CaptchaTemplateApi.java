package com.business.message.captcha.api;


import com.business.message.captcha.entity.CaptchaTemplate;
import com.core.module.common.valid.group.AddGroup;
import com.architecture.ultimate.mybatisplus.QueryParam;
import com.architecture.ultimate.query.common.model.Pagination;
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
@RestController()
@Api(tags = "验证码模板")
@RequestMapping("/captchaTemplateApi")
public interface CaptchaTemplateApi {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return 分页信息
     */
    @ApiOperation(value = "分页查询")
    @ApiOperationSupport(ignoreParameters = {"entity"})
    @PostMapping("/pageQuery")
    Pagination pageQuery(@RequestBody QueryParam<CaptchaTemplate> queryParam);

    /**
     * 添加验证码配置
     *
     * @param captchaEntity 配置信息
     */
    @ApiOperation(value = "添加验证码模板")
    @PostMapping("/add")
    void addCaptchaTemplate(@RequestBody @Validated(AddGroup.class) CaptchaTemplate captchaEntity);

    /**
     * 通过id(主键)查询验证码配置
     *
     * @param id 配置信息ID
     */
    @ApiOperation(value = "通过id(主键)查询验证码配置")
    @GetMapping("/findById/{id}")
    CaptchaTemplate findById(@PathVariable(name = "id") Integer id);

    /**
     * 修改验证码配置
     *
     * @param captchaEntity 修改的验证码配置信息
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改验证码模板")
    void updateCaptchaTemplate(@RequestBody CaptchaTemplate captchaEntity);

    /**
     * 通过主键ID删除
     *
     * @param id 主键ID
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "通过主键ID删除验证码模板")
    void delete(@PathVariable int id);
}
