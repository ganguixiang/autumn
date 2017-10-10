package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.IdEntity;
import com.ggxspace.autumn.entity.TreeEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单实体
 * Created by ganguixiang on 2017/10/9.
 */
@Entity
public class Menu extends TreeEntity {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单路径
     */
    private String url;

    /**
     * 菜单顺序
     */
    private Integer menuOrder;

    /**
     * 菜单类型
     * menu - 菜单
     * button - 按钮
     */
//    @Column(name = "type", length = 10, columnDefinition = "enum('menu', 'button')")
    private String type;

    /**
     * 权限字符串
     * menu例子：role:*
     * button例子：role:create，role:update，role:delete，role:view
     */
    private String permission;

    /**
     * 角色-菜单关系
     * 多对多
     * 关系在role中维护，所以menu是被维护方，role是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menus")
    private Set<Role> roles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", menuOrder=" + menuOrder +
                ", type='" + type + '\'' +
                ", permission='" + permission + '\'' +
                ", roles=" + roles +
                '}';
    }
}
