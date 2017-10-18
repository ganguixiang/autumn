package com.ggxspace.autumn.repository.system;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
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

//    @Query("select m from Menu m join m.roles r where r.id in :roleIds")
//    List<Menu> findByRoleIdsAndType(@Param("roleIds") List<String> roleIds, MenuTypeEnum type);

    List<Menu> findByRoles_idIn(List<String> roleIds);

    List<Menu> findByRoles_idInAndType(List<String> roleIds, MenuTypeEnum type);
}
