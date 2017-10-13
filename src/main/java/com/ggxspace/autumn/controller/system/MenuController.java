package com.ggxspace.autumn.controller.system;

import com.ggxspace.autumn.dto.system.RoleDTO;
import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.service.system.MenuService;
import com.ggxspace.autumn.service.system.RoleService;
import com.ggxspace.autumn.tree.MenuTree;
import com.ggxspace.autumn.tree.Tree;
import com.ggxspace.autumn.tree.TreeUtil;
import com.ggxspace.autumn.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单Controller
 * Created by ganguixiang on 2017/9/28.
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 跳转列表页
     * @return
     */
    @GetMapping("/")
    public ModelAndView goList() {
        return new ModelAndView("menu/list");
    }

    /**
     * 获取树形列表
     * @return
     */
    @GetMapping(value = "list")
    public Result list() {
        List<Menu> menus = menuService.findAll();
        List<MenuTree> menuTrees = TreeUtil.buildMenuTree(menus);
        return new Result<>(menuTrees);
    }

    /**
     * 保存
     * @param menu
     * @return
     */
    @PostMapping(value = "save")
    public Result save(@RequestBody Menu menu) {
        Menu m = menuService.save(menu);
        return new Result<>(m);
    }

    /**
     * 更新
     * @param menu
     * @return
     */
    @PostMapping(value = "update")
    public Result update(@RequestBody Menu menu) {
        Menu m = menuService.update(menu);
        return new Result<>(m);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "delete")
    public Result delete(String id) {
        menuService.delete(id);
        return new Result<>();
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @GetMapping(value = "update")
    public Result goUpdate(String id) {
        Menu menu = menuService.get(id);
        return new Result<>(menu);
    }
}
