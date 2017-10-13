package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.IdEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 1, max = 20, message = "角色名称长度必须在{min}和{max}之间")
    private String name;

    /**
     * 角色标识
     * 程序中判断使用，如"admin"，这个是唯一的
     */
    @Column(unique = true)
    @NotBlank(message = "角色标识不能为空")
    @Size(min = 1, max = 20, message = "角色标识长度必须在{min}和{max}之间")
    @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "角色标识只能输入字符和数字")
    private String code;

    /**
     * 角色-菜单关系
     * 多对多
     * 级联操作
     * 懒加载
     * 关系在role中维护，所以menu是被维护方，role是维护方
     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_menu",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id")
    )
    private Set<Menu> menus;

    /**
     * 角色-用户关系
     * 多对多
     * 级联操作
     * 懒加载
     * 关系在user中维护，所以role是被维护方，user是维护方
     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "roles")
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

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
