package io.github.architers.syscenter.user.api;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.context.web.PostMapping;
import io.github.architers.syscenter.user.domain.entity.SysTenant;
import io.github.architers.syscenter.user.service.SysTenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 租户对应的接口API
 *
 * @author luyi
 */
@RestController
@RequestMapping("/tenantApi")
public class TenantApi {

    @Resource
    private SysTenantService sysTenantService;

    /**
     * 分页查询租户列表
     */
    @PostMapping("/getTenantsByPage")
    public PageResult<SysTenant> getTenantsByPage(@Validated @RequestBody PageRequest<SysTenant> pageRequest) {
        return sysTenantService.getTenantsByPage(pageRequest);
    }

    /**
     * 添加租户
     */
    @PostMapping("/addTenant")
    public void addTenant(@RequestBody SysTenant sysTenant) {
        sysTenantService.addTenant(sysTenant);
    }

    /**
     * 编辑租户
     */
    @PutMapping("/editTenant")
    public void editTenant(@RequestBody SysTenant sysTenant) {
        sysTenantService.editTenant(sysTenant);
    }

    /**
     * 删除租户
     */
    @DeleteMapping("/deleteById")
    public void deleteById(@RequestParam("id") Integer id) {
        sysTenantService.deleteById(id);
    }
}
