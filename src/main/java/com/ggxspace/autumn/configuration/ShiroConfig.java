package com.ggxspace.autumn.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.service.system.MenuService;
import com.ggxspace.autumn.shiro.filter.AnyRolesAuthorizationFilter;
import com.ggxspace.autumn.shiro.filter.SimpleAnonymousFilter;
import com.ggxspace.autumn.shiro.filter.SimpleFormAuthenticationFilter;
import com.ggxspace.autumn.shiro.realm.WebShiroRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro配置
 * Created by ganguixiang on 2017/10/13.
 */
@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);


    @Autowired
    private MenuService menuService;

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自定义filter
        shiroFilterFactoryBean.getFilters().put("anyRoles", this.anyRolesAuthorizationFilter());
        shiroFilterFactoryBean.getFilters().put("authc", this.simpleFormAuthenticationFilter());
        //拦截器.
        Map<String,String> filterChainDefinitionMap = initChains();
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        LOGGER.info("shiro config init complete.");

        for (Map.Entry entry : filterChainDefinitionMap.entrySet()) {
            LOGGER.info("{} {}", entry.getKey(), entry.getValue());
        }

        return shiroFilterFactoryBean;
    }

    /**
     * 初始化过滤链
     * @return
     */
    public Map<String, String> initChains() {

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // 从数据库中加载菜单权限
        Map<String, String> dbChainMap = getDbChainMap();
        filterChainDefinitionMap.putAll(dbChainMap);

        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "user");

        return filterChainDefinitionMap;
    }


    /**
     * 从数据库中加载菜单权限
     * @return
     */
    private Map<String,String> getDbChainMap() {
        // 获取所有的菜单
        List<Menu> menus = menuService.findAll();
        // 对菜单列表进行排序，菜单类型的放在最后面
        Collections.sort(menus, new MenuComparator());

        /**
         * 数据库中配置的过滤链
         */
        Map<String,String> dbChainMap = new LinkedHashMap<>();

        /**
         * 遍历菜单列表，对于存在url和已经分配角色的路径进行权限控制
         */
        menus.stream().forEach(m -> {
            if (StringUtils.isNotEmpty(m.getUrl())) {
                StringBuilder sb = new StringBuilder(m.getUrl());
                // 菜单类型，需要在url后面加上/**
                if (m.getType().equals(MenuTypeEnum.MENU)) {
                    if (!m.getUrl().endsWith("/")) {
                        sb.append("/");
                    }
                    sb.append("**");
                }
                // 路径
                String url = sb.toString();
                StringBuilder roleOrFilters = new StringBuilder();
                // 得到菜单分配的角色列表
                List<Role> roles = new ArrayList<>(m.getRoles());
                for (int i = 0; i < roles.size(); i++) {
                    if (i == 0) {
                        roleOrFilters.append(roles.get(i).getCode());
                    } else {
                        roleOrFilters.append(",").append(roles.get(i).getCode());
                    }
                }
                String rolesStr = roleOrFilters.toString();
                // 如果已经分配角色，则加上权限控制
                // TODO 没有分配角色，是否需要加上auth?
                if (StringUtils.isNotEmpty(rolesStr)) {
                    // 自行搜索roleOrFilter的说明
                    dbChainMap.put(url, "anyRoles["+rolesStr+"]"); //  /discover/newstag  authc,roles[user,admin],roleOrFilter[user,admin]
                }
            }
        });

        return dbChainMap;
    }

    @Bean
    public AnyRolesAuthorizationFilter anyRolesAuthorizationFilter() {
        return new AnyRolesAuthorizationFilter();
    }

    @Bean
    public SimpleFormAuthenticationFilter simpleFormAuthenticationFilter() {
        return new SimpleFormAuthenticationFilter();
    }

    @Bean
    public SimpleAnonymousFilter simpleAnonymousFilter() {
        return new SimpleAnonymousFilter();
    }

    @Bean
    public WebShiroRealm webShiroRealm() {
        WebShiroRealm webShiroRealm = new WebShiroRealm();
        return webShiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.webShiroRealm());

        return securityManager;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 菜单比较器，权限类型菜单大于菜单类型菜单
     */
    class MenuComparator implements Comparator<Menu> {

        @Override
        public int compare(Menu o1, Menu o2) {
            if (!o1.getType().equals(o2.getType())) {
                if (o1.getType().equals(MenuTypeEnum.MENU)) {
                    return 1;
                } else {
                    return -1;
                }
            }

            return 0;
        }
    }
}
