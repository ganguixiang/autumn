package com.ggxspace.autumn.service.system.impl;

import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.exception.AutumnException;
import com.ggxspace.autumn.repository.system.RoleRepository;
import com.ggxspace.autumn.service.AbstractServiceImpl;
import com.ggxspace.autumn.service.system.FilterChainDefinitionsService;
import com.ggxspace.autumn.service.system.RoleService;
import com.ggxspace.autumn.util.ObjectUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色service
 * Created by ganguixiang on 2017/9/27.
 */
@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Boolean validateCode(String id, String code) {
        List<Role> roles = roleRepository.findByIdNotAndCode(id, code);
        if (CollectionUtils.isEmpty(roles)) {
            return true;
        }
        return false;
    }

    /**
     * 删除角色，角色下还有用户不允许删除
     * @param id 主键
     */
    public void delete(String id) {

        Role role = super.get(id);
        if (ObjectUtils.nonNull(role.getUsers())) {
            throw new AutumnException("该角色下还有用户，不允许删除");
        }
        super.delete(id);
    }
}
