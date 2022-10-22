package io.github.architers.center.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.component.mybatisplus.InsertBatch;

public interface MenuDao extends InsertBatch<Menu>, BaseMapper<Menu> {
}
