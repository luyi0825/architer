package io.github.architers.center.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.domain.vo.MenuNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao extends  BaseMapper<Menu> {
    List<MenuNode> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}
