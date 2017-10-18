package com.ggxspace.autumn.controller;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.service.system.MenuService;
import com.ggxspace.autumn.tree.MenuTree;
import com.ggxspace.autumn.tree.TreeUtils;
import com.ggxspace.autumn.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 首页Controller
 * Created by ganguixiang on 2017/9/28.
 */
@RestController
public class IndexController extends CommonController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public ModelAndView index(Model model) {
        return new ModelAndView("index");
    }

    /**
     * 获取当前用户所拥有的菜单
     * @return
     */
    @GetMapping("/user-menus")
    public Result userMenus() {
        // 获取当前用户所拥有的菜单
        List<Menu> menus = menuService.findByRoleIdsAndType(getCurrentUserRoleIds(), MenuTypeEnum.MENU);
        List<MenuTree> menuTrees = TreeUtils.buildMenuTree(menus);
        return new Result.Builder().data(menuTrees).build();
    }
}
