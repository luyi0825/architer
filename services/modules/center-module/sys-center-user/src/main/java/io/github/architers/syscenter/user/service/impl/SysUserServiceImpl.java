package io.github.architers.syscenter.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.syscenter.user.TenantUserConstants;
import io.github.architers.syscenter.user.dao.SysUserDao;
import io.github.architers.syscenter.user.domain.dto.AddTenantUserDTO;
import io.github.architers.syscenter.user.domain.dto.SysUserQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.vo.SysUserVO;
import io.github.architers.syscenter.user.service.SysTenantService;
import io.github.architers.syscenter.user.service.SysTenantUserService;
import io.github.architers.syscenter.user.service.SysUserService;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;


/**
 * @author luyi
 */
@Service
public class SysUserServiceImpl implements SysUserService {


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysTenantService sysTenantService;

    @Resource
    private SysTenantUserService sysTenantUserService;


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
    @Transactional(rollbackFor = Exception.class)
    public void addSysUser(AddTenantUserDTO add) {

        //判断租户状态
        sysTenantService.isValid(add.getTenantId());
        //判断用户名是否已经存在
        SysUserVO sysUserVO = sysUserDao.selectByUserName(add.getUserName());
        Date date = new Date();
        //用户不存在，就直接添加
        if (sysUserVO == null) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(add.getUserName());
            sysUser.setUserCaption(add.getUserCaption());
            sysUser.setStatus(add.getStatus());
            sysUser.fillCreateAndUpdateField(date);
            //默认的密码为系统的默认的密码+用户名
            String defaultPassword = passwordEncoder.encode(TenantUserConstants.DEFAULT_PASSWORD_PREFIX + add.getUserName());
            sysUser.setPassword(defaultPassword);
            sysUserDao.insert(sysUser);
            //插入租户用户数据
            SysTenantUser sysTenantUser = new SysTenantUser();
            sysTenantUser.setUserId(sysUser.getId());
            sysTenantUser.setTenantId(add.getTenantId());
            sysTenantUser.fillCreateAndUpdateField(date);
            sysTenantUser.setStatus(add.getStatus());
            sysTenantUserService.insertOne(sysTenantUser);
            return;
        }
        //用户已经存在

        //判断该租户下是否已经存在用户
        long count = sysTenantUserService.countByUserId(sysUserVO.getId());
        if (count > 0) {
            throw new BusException("系统用户已经存在");
        }
        //插入租户用户数据
        SysTenantUser sysTenantUser = new SysTenantUser();
        sysTenantUser.setUserId(sysUserVO.getId());
        sysTenantUser.setTenantId(add.getTenantId());
        sysTenantUser.fillCreateAndUpdateField(date);
        sysTenantUserService.insertOne(sysTenantUser);
    }

    @Override
    public void editUser(SysUser edit) {
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
