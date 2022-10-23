package io.github.architers.center.menu.service;

import io.github.architers.center.menu.domain.dto.SysUserQueryDTO;
import io.github.architers.center.menu.domain.entity.SysUser;
import io.github.architers.center.menu.domain.vo.SysUserVO;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author luyi
 */
public interface SysUserService {
    /**
     * 分页查询系统用户
     */
    PageResult<SysUserVO> getUsersByPage(PageRequest<SysUserQueryDTO> pageRequest);

    void addSysUser(SysUser sysUser);

    void editUser(SysUser edit);


    void changUserStatus(Long userId, Byte status);

    SysUser selectByUserName(String userName);
}
