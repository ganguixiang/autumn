package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.IdEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色实体
 * Created by ganguixiang on 2017/9/27.
 */
@Entity
public class Role extends IdEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     * 程序中判断使用，如"admin"，这个是唯一的
     */
    @Column(name = "code", length = 20, unique = true)
    private String code;

    /**
     * 角色-菜单关系
     * 多对多
     * 级联操作
     * 懒加载
     * 关系在role中维护，所以menu是被维护方，role是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private Set<Menu> menus = new HashSet<>();

    /**
     * 角色-用户关系
     * 多对多
     * 级联操作
     * 懒加载
     * 关系在user中维护，所以role是被维护方，user是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
