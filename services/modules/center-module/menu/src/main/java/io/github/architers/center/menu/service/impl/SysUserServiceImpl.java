package io.github.architers.center.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.center.menu.dao.SysUserDao;
import io.github.architers.center.menu.domain.dto.SysUserQueryDTO;
import io.github.architers.center.menu.domain.entity.SysUser;
import io.github.architers.center.menu.domain.vo.SysUserVO;
import io.github.architers.center.menu.service.SysUserService;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;


/**
 * @author luyi
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public PageResult<SysUserVO> getUsersByPage(PageRequest<SysUserQueryDTO> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), new Supplier<List<SysUserVO>>() {
            @Override
            public List<SysUserVO> get() {
                SysUserQueryDTO sysUserQueryDTO = pageRequest.getRequestParam();
                if (sysUserQueryDTO == null) {
                    sysUserQueryDTO = new SysUserQueryDTO();
                }
                sysUserQueryDTO.setTenantId(TenantUtils.getTenantId());
                return sysUserDao.getUsersByPage(sysUserQueryDTO);
            }
        });
    }

    @Override
    public void addSysUser(SysUser sysUser) {
        //判断用户名是否已经存在
        int count = sysUserDao.countByUsername(sysUser.getUserName());
        if (count > 0) {
            throw new BusException("用户名【" + sysUser.getUserName() + "】已经存在");
        }
        sysUser.fillCreateAndUpdateField(new Date());
        sysUser.setTenantId(TenantUtils.getTenantId());
        sysUser.setPassword(UUID.randomUUID().toString());
        sysUserDao.insert(sysUser);
    }

    @Override
    public void editUser(SysUser edit) {
        edit.setTenantId(null);
        edit.setUserName(null);
        edit.fillCreateAndUpdateField(new Date());
        int count = sysUserDao.updateById(edit);
        if (count != 1) {
            throw new BusException("更新用户失败");
        }
    }

    @Override
    public void changUserStatus(Long userId, Byte status) {
        SysUser sysUser = new SysUser();
        sysUser.setStatus(status);
        sysUser.setId(userId);
        sysUser.fillCreateAndUpdateField(new Date());
        sysUserDao.updateById(sysUser);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        Wrapper<SysUser> sysUserWrapper =
                Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserName, userName);
        return sysUserDao.selectOne(sysUserWrapper);
    }
}
