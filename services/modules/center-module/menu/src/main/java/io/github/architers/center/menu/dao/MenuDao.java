package io.github.architers.center.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.domain.vo.MenuNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu> {
    /**
     * 选取角色对应的菜单节点数据
     *
     * @param roleIds 角色ID
     * @return 菜单节点
     */
    List<MenuNode> selectByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 通过parentCode查询菜单
     *
     * @param tenantId   租户ID
     * @param parentCode 父级code
     * @return 菜单集合
     */
    List<Menu> selectByParentCode(@Param("tenantId") Integer tenantId,
                                  @Param("parentCode") String parentCode);
}
