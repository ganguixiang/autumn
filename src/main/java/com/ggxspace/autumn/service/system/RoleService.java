package com.ggxspace.autumn.service.system;

import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.service.AbstractService;

/**
 * 角色service
 * Created by ganguixiang on 2017/9/27.
 */
public interface RoleService extends AbstractService<Role> {

    Boolean validateCode(String id, String code);

}
