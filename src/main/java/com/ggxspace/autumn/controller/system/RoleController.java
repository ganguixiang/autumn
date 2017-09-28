package com.ggxspace.autumn.controller.system;

import com.ggxspace.autumn.dto.system.RoleDTO;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色Controller
 * Created by ganguixiang on 2017/9/28.
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/")
    public String list() {
        return "role/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public List<Role> save() {
        return roleService.findAll();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Role save(Role role) {
        roleService.save(role);
        return role;
    }

    @RequestMapping(value = "detail")
    @ResponseBody
    public RoleDTO detail(String id) {
        Role role = roleService.get(id);
        return new RoleDTO(role);
    }
}
