package com.ggxspace.autumn.repository.system;

import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.repository.AbstractRepository;

import java.util.List;

/**
 * 角色仓库接口
 * Created by ganguixiang on 2017/9/27.
 */
public interface RoleRepository extends AbstractRepository<Role>, RoleRepositoryCustom {

    List<Role> findByIdNotAndCode(String id, String code);
}
