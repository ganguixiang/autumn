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
     * 角色标识
     * 程序中判断使用，如"admin"，这个是唯一的
     */
    @Column(name = "role", length = 20, unique = true)
    private String role;

    /**
     * 角色-权限关系
     * 多对多
     * 级联操作
     * 立即加载
     * 关系在role中维护，所以permission是被维护方，role是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>();

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
