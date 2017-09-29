package com.ggxspace.autumn.repository.system.impl;

import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.repository.AbstractRepositoryImpl;
import com.ggxspace.autumn.repository.system.RoleRepositoryCustom;
import org.springframework.stereotype.Repository;

/**
 * 角色仓库实现
 * Created by ganguixiang on 2017/9/27.
 */
@Repository
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepositoryCustom {
}
