package com.ggxspace.autumn.controller.system;

import com.ggxspace.autumn.dto.system.RoleDTO;
import com.ggxspace.autumn.dto.system.UserDTO;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.service.system.RoleService;
import com.ggxspace.autumn.service.system.UserService;
import com.ggxspace.autumn.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 * Created by ganguixiang on 2017/9/28.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * 跳转列表页
     * @return
     */
    @GetMapping("/")
    public ModelAndView goList() {
        return new ModelAndView("user/list");
    }

    /**
     * 获取列表
     * @return
     */
    @GetMapping(value = "list")
    public Result list() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = UserDTO.toUserDTO(users);
        return new Result<>(userDTOS);
    }

    /**
     * 保存
     * @param user
     * @return
     */
    @PostMapping(value = "save")
    public Result save(@RequestBody User user) {
        User u = userService.save(user);
        return new Result<>(u);
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping(value = "update")
    public Result update(@RequestBody User user) {
        User u = userService.update(user);
        return new Result<>(u);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "delete")
    public Result delete(String id) {
        userService.delete(id);
        return new Result<>();
    }

    /**
     * 更新
     * @param id
     * @return
     */
    @GetMapping(value = "update")
    public Result goUpdate(String id) {
        User user = userService.get(id);
        return new Result<>(user);
    }

    /**
     * 跳转修改用户角色页面
     * @param id
     * @return
     */
    @GetMapping(value = "go-select-role")
    public Result goSelectRole(String id) {
        User user = userService.get(id);
        List<Role> roles = roleService.findAll();
        List<RoleDTO> roleDTOS = RoleDTO.toRoleDTO(roles);
        List<Role> rs = user.getRoles();
        List<String> selectedRoles = new ArrayList<>();
        RoleDTO.toRoleDTO(rs).stream().forEach(r -> selectedRoles.add(r.getId()));

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roles", roleDTOS);
        map.put("selectedRoles", selectedRoles);

        return new Result(map);
    }

    /**
     * 更新用户角色
     * @param id
     * @param roleIds
     * @return
     */
    @PostMapping(value = "update-role")
    public Result updateRole(String id, @RequestBody String[] roleIds) {
        User user = userService.get(id);
        List<Role> roles = roleService.findAll(Arrays.asList(roleIds));
        user.setRoles(roles);
        userService.update(user);
        return new Result(user);
    }

    /**
     * 校验用户名是否唯一
     * @param id
     * @param username
     * @return
     */
    @GetMapping(value = "validate-username")
    public Result validateUsername(String id, String username) {
        boolean result = userService.validateUsername(id, username);
        return new Result(result);
    }

}
