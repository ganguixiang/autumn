package com.ggxspace.autumn.shiro.realm;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.shiro.ShiroUser;
import com.ggxspace.autumn.shiro.ShiroUtils;
import com.ggxspace.autumn.shiro.factory.ShiroFactory;
import com.ggxspace.autumn.shiro.factory.impl.ShiroFactoryImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by ganguixiang on 2017/10/13.
 */
public class WebShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebShiroRealm.class);

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前登陆的shiroUser
        ShiroUser ShiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        // 新建授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 将当前登陆的用户的角色列表放入授权信息中
        info.addRoles(ShiroUser.getRoles());

        return info;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        // 获取shiroFactory
        ShiroFactory shiroFactory = ShiroFactoryImpl.getInstance();

        // 得到token
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        // 根据用户名获取user
        User user = shiroFactory.getUser(usernamePasswordToken.getUsername());

        // 创建ShiroUser
        ShiroUser shiroUser = shiroFactory.createShiroUser(user);

        // 登陆
        return shiroFactory.login(shiroUser, user, super.getName());

    }

    /**
     * 设定password校验的hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroUtils.HASH_ALGORITHM);
        matcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);

        setCredentialsMatcher(matcher);
    }

}
