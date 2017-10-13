package com.ggxspace.autumn.shiro.factory;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.shiro.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * Created by ganguixiang on 2017/10/13.
 */
public interface ShiroFactory {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUser(String username);

    /**
     * 创建ShiroUser
     * @param user
     * @return
     */
    ShiroUser createShiroUser(User user);

    /**
     * 登陆
     * @param shiroUser
     * @param user
     * @param realmName
     * @return
     */
    SimpleAuthenticationInfo login(ShiroUser shiroUser, User user, String realmName);
}
