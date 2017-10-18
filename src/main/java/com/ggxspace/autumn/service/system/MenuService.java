package com.ggxspace.autumn.service.system;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.service.AbstractService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 菜单service
 * Created by ganguixiang on 2017/9/27.
 */
public interface MenuService extends AbstractService<Menu> {


    List<Menu> findByRoleIdsAndType(List<String> roleIds, MenuTypeEnum type);
}
