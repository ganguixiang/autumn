package com.ggxspace.autumn.controller.system;

import com.ggxspace.autumn.dto.system.RoleDTO;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.service.system.RoleService;
import com.ggxspace.autumn.vo.Result;
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

import java.util.List;

/**
 * 角色Controller
 * Created by ganguixiang on 2017/9/28.
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("role/list");
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping(value = "list")
    public Result save() {
        List<Role> roles = roleService.findAll();
        return new Result<>(roles);
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
    @DeleteMapping(value = {"delete/{id}"})
    public Result delete(@PathVariable String id) {
        roleService.delete(id);
        return new Result<>();
    }

    /**
     * 根据主键获取详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result detail(@PathVariable String id) {
        Role role = roleService.get(id);
        RoleDTO dto = new RoleDTO(role);
        return new Result<>(dto);
    }
}
