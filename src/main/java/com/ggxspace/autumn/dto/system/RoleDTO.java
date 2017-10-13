package com.ggxspace.autumn.dto.system;

import com.ggxspace.autumn.entity.system.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganguixiang on 2017/9/28.
 */
public class RoleDTO extends BaseDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 标识
     */
    private String code;

    public RoleDTO(Role role) {
        super(role);
        this.name = role.getName();
        this.code = role.getCode();
    }

    public static List<RoleDTO> toRoleDTO(List<Role> roles) {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        roles.stream().forEach(r -> roleDTOS.add(new RoleDTO(r)));
        return roleDTOS;
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

    @Override
    public String toString() {
        return "RoleDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
