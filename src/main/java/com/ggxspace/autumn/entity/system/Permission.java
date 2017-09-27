package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.IdEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限实体
 * Created by ganguixiang on 2017/9/27.
 */
@Entity
public class Permission extends IdEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 资源类型
     * menu - 菜单权限
     * button - 按钮权限
     */
//    @Column(name = "resource_type", length = 10, columnDefinition = "enum('menu', 'button')")
    private String resourceType;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串
     * menu例子：role:*
     * button例子：role:create，role:update，role:delete，role:view
     */
    private String permission;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 父id列表
     */
    private String parentIds;

    /**
     * 角色-权限关系
     * 多对多
     * 关系在role中维护，所以permission是被维护方，role是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permissions")
    private List<Role> roles = new ArrayList<>();

    public Permission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
