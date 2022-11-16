package io.github.architers.syscenter.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.component.mybatisplus.Column;
import io.github.architers.component.mybatisplus.InsertBatch;
import io.github.architers.syscenter.user.domain.dto.SysUserQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.vo.SysUserPageVO;
import io.github.architers.syscenter.user.domain.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luyi
 */
public interface SysUserDao extends BaseMapper<SysUser> , InsertBatch<SysUser> {

    /**
     * 分页查询系统用户
     *
     * @param requestParam 系统查询用户的参数
     * @return 分页集合
     */
    List<SysUserPageVO> getUsersByPage(SysUserQueryDTO requestParam);


    int countByUsername(String username);

    SysUserVO selectByUserName(@Param("userName") String userName);
}
