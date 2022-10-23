package io.github.architers.center.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.menu.domain.dto.SysUserQueryDTO;
import io.github.architers.center.menu.domain.entity.SysUser;
import io.github.architers.center.menu.domain.vo.SysUserVO;

import java.util.List;

/**
 * @author luyi
 */
public interface SysUserDao extends BaseMapper<SysUser> {
    List<SysUserVO> getUsersByPage(SysUserQueryDTO requestParam);


    int countByUsername(String username);
}
