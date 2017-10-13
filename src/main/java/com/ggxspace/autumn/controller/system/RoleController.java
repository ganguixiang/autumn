package com.ggxspace.autumn.controller.system;

import com.ggxspace.autumn.dto.system.RoleDTO;
import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.service.system.MenuService;
import com.ggxspace.autumn.service.system.RoleService;
import com.ggxspace.autumn.tree.MenuTree;
import com.ggxspace.autumn.tree.TreeUtil;
import com.ggxspace.autumn.vo.Result;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色Controller
 * Created by ganguixiang on 2017/9/28.
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 跳转列表页
     * @return
     */
    @GetMapping("/")
    public ModelAndView goList() {
        return new ModelAndView("role/list");
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping(value = "list")
    public Result list() {
        List<Role> roles = roleService.findAll();
        List<RoleDTO> roleDTOS = RoleDTO.toRoleDTO(roles);
        return new Result<>(roleDTOS);
    }

    /**
     * 保存
     * @param role
     * @return
     */
    @PostMapping(value = "save")
    public Result save(@RequestBody Role role) {
        Role r = roleService.save(role);
        return new Result<>(r);
    }

    /**
     * 更新
     * @param role
     * @return
     */
    @PostMapping(value = "update")
    public Result update(@RequestBody Role role) {
        Role r = roleService.update(role);
        return new Result<>(r);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = {"delete"})
    public Result delete(String id) {
        roleService.delete(id);
        return new Result<>();
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @GetMapping(value = "update")
    public Result goUpdate(String id) {
        Role role = roleService.get(id);
        RoleDTO dto = new RoleDTO(role);
        return new Result<>(dto);
    }

    /**
     * 跳转设置权限页面
     * @param id
     * @return
     */
    @GetMapping(value = "go-select-menu")
    public Result goSelectMenu(String id) {
        Role role = roleService.get(id);
        List<Menu> menus = menuService.findAll();
        List<MenuTree> menuTrees = TreeUtil.buildMenuTree(menus);
        Set<Menu> ms = role.getMenus();
        List<String> selectedMenus = new ArrayList<>();

        ms.stream().forEach(m -> selectedMenus.add(m.getId()));

        Map<String, Object> map = new HashMap<>();
        map.put("role", role);
        map.put("menuTrees", menuTrees);
        map.put("selectedMenus", selectedMenus);

        return new Result(map);
    }

    /**
     * 更新角色权限
     * @param id
     * @param menuIds
     * @return
     */
    @PostMapping(value = "update-menu")
    public Result updateMenu(String id, @RequestBody String[] menuIds) {
        Role role = roleService.get(id);
        List<Menu> menus = menuService.findAll(Arrays.asList(menuIds));
        role.setMenus(new HashSet<>(menus));
        roleService.update(role);
        return new Result(role);
    }

    /**
     * 验证code是否已经存在
     * @param code
     * @return
     */
    @GetMapping(value = "validate-code")
    public Result validateCode(String id, String code) {
        Boolean result = roleService.validateCode(id, code);
        return new Result(result);
    }
}
