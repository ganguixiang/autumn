package com.ggxspace.autumn.service.system.impl;

import com.ggxspace.autumn.configuration.ShiroConfig;
import com.ggxspace.autumn.service.system.FilterChainDefinitionsService;
import com.ggxspace.autumn.util.SpringContextUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * shiro过滤器服务
 * 重新加载权限配置，修改权限后不需要重启服务就可以生效
 * Created by ganguixiang on 2017/10/16.
 */
@Service
public class FilterChainDefinitionsServiceImpl implements FilterChainDefinitionsService {

    @Autowired
    private ShiroConfig shiroConfig;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    /**
     * 重新加载权限配置，修改权限后不需要重启服务就可以生效
     */
    public void reloadFilterChains() {
        synchronized (shiroFilterFactoryBean) {
            try {
                AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
                PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
                DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
                // 清除权限配置
                filterChainManager.getFilterChains().clear();
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                // 获取权限配置
                Map<String, String> chainsMap = shiroConfig.initChains();
                // 重新设置权限
                shiroFilterFactoryBean.setFilterChainDefinitionMap(chainsMap);
                for (Map.Entry<String, String> entry : chainsMap.entrySet()) {
                    filterChainManager.createChain(entry.getKey(), entry.getValue().replace(" ", ""));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
