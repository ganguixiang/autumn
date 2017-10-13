package com.ggxspace.autumn.service.system.impl;

import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.repository.system.RoleRepository;
import com.ggxspace.autumn.service.AbstractServiceImpl;
import com.ggxspace.autumn.service.system.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
}
