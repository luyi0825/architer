package io.github.architers.center.menu.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.center.menu.dao.SysRoleDao;
import io.github.architers.center.menu.domain.dto.SysRoleQueryDTO;
import io.github.architers.center.menu.domain.entity.SysRole;
import io.github.architers.center.menu.service.SysRoleService;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.exception.NoLogStackException;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.context.web.ServletUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author luyi
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public Integer addRole(SysRole add) {
        //判断角色英文是否重复
        int count = sysRoleDao.countByRoleName(TenantUtils.getTenantId(), add.getRoleName());
        if (count > 0) {
            throw new NoLogStackException("角色英文名称重复");
        }
        add.setTenantId(TenantUtils.getTenantId());
        add.fillCreateAndUpdateField(new Date());
        sysRoleDao.insert(add);
        return add.getId();
    }

    @Override
    public PageResult<SysRole> getRolesByPage(PageRequest<SysRoleQueryDTO> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(),
                () -> {
                    SysRoleQueryDTO param = pageRequest.getRequestParam();
                    Wrapper<SysRole> wrapper =
                            Wrappers.lambdaQuery(SysRole.class).eq(SysRole::getTenantId,
                                            TenantUtils.getTenantId())
                                    .like(StringUtils.isNotBlank(param.getRoleName()), SysRole::getRoleName, param.getRoleName())
                                    .like(StringUtils.isNotBlank(param.getRoleCaption()), SysRole::getRoleCaption, param.getRoleCaption())
                                    .eq(param.getStatus() != null, SysRole::getStatus, param.getStatus());
                    return sysRoleDao.selectList(wrapper);
                });
    }

    @Override
    public void editRole(SysRole edit) {
        edit.setTenantId(null);
        edit.setRoleName(null);
        edit.fillCreateAndUpdateField(new Date());
        sysRoleDao.updateById(edit);
    }
}
