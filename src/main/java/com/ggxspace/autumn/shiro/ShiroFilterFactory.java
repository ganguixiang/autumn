package com.ggxspace.autumn.shiro;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.service.system.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 自定义shiro过滤器工厂
 * 1.从数据库加载所有菜单，并对其进行权限控制
 * 2.加载配置文件中的配置，如配置登陆页面，登陆成功跳转页等
 * Created by ganguixiang on 2017/10/12.
 */
//@Component
public class ShiroFilterFactory extends ShiroFilterFactoryBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterFactory.class);

    /**记录配置中的过滤链*/
    public static String definition = "";

    @Autowired
    private MenuService menuService;

    /**
     * 初始化设置过滤链
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {

        definition = definitions;//记录配置的静态过滤链
        // 获取所有的菜单
        List<Menu> menus = menuService.findAll();
        // 对菜单列表进行排序，菜单类型的放在最后面
        Collections.sort(menus, new MenuComparator());

        /**
         * 数据库中配置的过滤链
         */
        Map<String,String> dbChains = new LinkedHashMap<>();

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
                    dbChains.put(url, "roleOrFilter["+rolesStr+"]"); //  /discover/newstag  authc,roles[user,admin],roleOrFilter[user,admin]
                }
            }
        });


        // 加载配置默认的过滤链
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        for (Map.Entry<String, String> entry : dbChains.entrySet()) {
            LOGGER.info("{} {}", entry.getKey(), entry.getValue());
        }
        // 加上数据库中过滤链
        section.putAll(dbChains);
        // 其余的路径都可以匿名访问
        section.put("/**", "anon");
        setFilterChainDefinitionMap(section);
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
