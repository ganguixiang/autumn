package com.ggxspace.autumn.dto.system;

import com.ggxspace.autumn.entity.system.Role;

/**
 * Created by ganguixiang on 2017/9/28.
 */
public class RoleDTO extends BaseDTO {

    /**
     * 名称
     */
    private String role;

    public RoleDTO(Role role) {
        super(role);
        this.role = role.getRole();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
