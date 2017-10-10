package com.ggxspace.autumn.service.system.impl;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.repository.system.MenuRepository;
import com.ggxspace.autumn.service.AbstractServiceImpl;
import com.ggxspace.autumn.service.system.MenuService;
import com.ggxspace.autumn.service.system.UserService;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单service实现
 * Created by ganguixiang on 2017/9/27.
 */
@Service
public class MenuServiceImpl extends AbstractServiceImpl<Menu> implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 保存
     * @param menu
     * @return
     */
    public Menu save(Menu menu) {
        if (StringUtils.isNotEmpty(menu.getParentId())) {
            // 设置parentIds
            // 数据库查询得到parent，该菜单的parentIds等于parent的parentIds加上parent的id，逗号分割
            Menu parent = super.get(menu.getParentId());
            // 格式：8a8abca65f042848015f042969a70000,8a8abca65f042c69015f042d4ea90000
            String parentIds = "";
            if (StringUtils.isNotEmpty(parent.getParentIds())) {
                parentIds = String.format("%s,%s", parent.getParentIds(), parent.getId());
            } else {
                parentIds = String.format("%s", parent.getId());
            }
            menu.setParentIds(parentIds);
        }

        // 设置label
        menu.setLabel(menu.getName());

        // 设置level
        if (StringUtils.isNotEmpty(menu.getParentIds())) {
            String[] parents = menu.getParentIds().split(",");
            menu.setLevel(parents.length);
        } else {
            menu.setLevel(0);
        }

        // 保存
        super.save(menu);
        return menu;
    }

    /**
     * 更新
     * 当前菜单的子菜单数量不为0，不允许修改当前菜单的上级菜单
     * @param menu
     * @return
     */
    public Menu update(Menu menu) {
        if (StringUtils.isNotEmpty(menu.getParentId()) && menu.getParentId().equals(menu.getId())) {
            throw new RuntimeException("请不要选择当前菜单作为上级菜单");
        }
        Menu m = super.get(menu.getId());
        // 修改父菜单，当菜单的子菜单数量为0时才允许修改
        if (!m.getParentId().equals(menu.getParentId())) {
            List<Menu> children = menuRepository.findByParentId(menu.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                throw new RuntimeException("当前菜单还有子菜单，不允许修改当前菜单的上级菜单");
            }
        }
        // 更新
        super.update(menu);
        return menu;
    }

    /**
     * 删除，存在子菜单不允许删除
     * @param id 主键
     */
    public void delete(String id) {
        List<Menu> children = menuRepository.findByParentId(id);
        if (CollectionUtils.isNotEmpty(children)) {
            throw new RuntimeException("当前菜单还有子菜单，不允许删除");
        }
        super.delete(id);
    }
}
