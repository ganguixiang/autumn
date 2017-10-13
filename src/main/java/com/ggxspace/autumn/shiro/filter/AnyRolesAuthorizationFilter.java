package com.ggxspace.autumn.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * 自定义filter
 * 只要包含其中任何一个角色就可以通过
 * Created by ganguixiang on 2017/10/13.
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);
        String[] roles = (String[]) mappedValue;
        if (null == roles || roles.length == 0) {
            // 没有定义，直接返回true
            return true;
        }

        for (String role : roles) {
            // 包含其中一个角色，返回true
            if (subject.hasRole(role)) {
                return true;
            }
        }

        return false;
    }
}
