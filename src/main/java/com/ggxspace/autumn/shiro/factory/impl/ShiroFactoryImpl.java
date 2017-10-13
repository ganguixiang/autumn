package com.ggxspace.autumn.shiro.factory.impl;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.enums.UserStateEnum;
import com.ggxspace.autumn.exception.AutumnException;
import com.ggxspace.autumn.repository.system.UserRepository;
import com.ggxspace.autumn.service.system.UserService;
import com.ggxspace.autumn.shiro.ShiroUser;
import com.ggxspace.autumn.shiro.factory.ShiroFactory;
import com.ggxspace.autumn.util.ObjectUtils;
import com.ggxspace.autumn.util.SpringContextUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ganguixiang on 2017/10/13.
 */
@Service
@Transactional(readOnly = true)
public class ShiroFactoryImpl implements ShiroFactory {

    @Autowired
    private UserService userService;

    public static ShiroFactory getInstance() {
        return SpringContextUtils.getBean(ShiroFactory.class);
    }


    public User getUser(String username) {
        ObjectUtils.requireNonNull(username);
        User user = userService.findByUsername(username);

        if (ObjectUtils.isNull(user)) {
            throw new AutumnException("用户不存在");
        }

        if (UserStateEnum.DISABLE.equals(user.getState())) {
            throw new AutumnException("用户已经被禁用");
        }

        return user;
    }

    public ShiroUser createShiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser(user);
        // 把角色列表放入shiroUser中
        user.getRoles().stream().forEach(r -> shiroUser.getRoles().add(r.getCode()));
        return shiroUser;
    }


    public SimpleAuthenticationInfo login(ShiroUser shiroUser, User user, String realmName) {
        // 密码
        String credentials = user.getPassword();
        // 密码加盐处理
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
