package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.TreeEntity;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 1, max = 20, message = "角色名称长度必须在{min}和{max}之间")
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单路径
     * 唯一
     */
    @Column(unique = true)
    @NotBlank(message = "路径不能为空")
    @Pattern(regexp = "^/.*?", message = "无效的路径地址")
    private String url;

    /**
     * 菜单顺序
     */
    @Min(value = 1, message = "顺序在1-10之间")
    @Max(value = 10, message = "顺序在1-10之间")
    private Integer menuOrder;

    /**
     * 菜单类型
     * MENU - 菜单
     * PERMISSION - 权限
     */
    @NotNull(message = "类型不能为空")
    @Enumerated(EnumType.STRING)
    private MenuTypeEnum type;

    /**
     * 角色-菜单关系
     * 多对多
     */
    @ManyToMany(mappedBy = "menus", fetch = FetchType.EAGER)
    private Set<Role> roles;

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

    public MenuTypeEnum getType() {
        return type;
    }

    public void setType(MenuTypeEnum type) {
        this.type = type;
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
                ", roles=" + roles +
                '}';
    }
}
