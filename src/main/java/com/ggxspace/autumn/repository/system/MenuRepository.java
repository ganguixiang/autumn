package com.ggxspace.autumn.repository.system;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.repository.AbstractRepository;

import java.util.List;

/**
 * 用户仓库接口
 * Created by ganguixiang on 2017/9/27.
 */
public interface MenuRepository extends AbstractRepository<Menu>, MenuRepositoryCustom {
    /**
     * 根据id查询子菜单
     * @param parentId
     * @return
     */
    List<Menu> findByParentId(String parentId);
}
