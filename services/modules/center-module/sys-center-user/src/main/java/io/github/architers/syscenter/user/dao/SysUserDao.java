package io.github.architers.syscenter.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.syscenter.user.domain.dto.SysUserQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luyi
 */
public interface SysUserDao extends BaseMapper<SysUser> {
    List<SysUserVO> getUsersByPage(SysUserQueryDTO requestParam);


    int countByUsername(String username);

    SysUserVO selectByUserName(@Param("userName") String userName);
}
