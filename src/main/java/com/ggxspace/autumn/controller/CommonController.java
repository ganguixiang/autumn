package com.ggxspace.autumn.controller;

import com.ggxspace.autumn.shiro.ShiroUser;
import com.ggxspace.autumn.shiro.ShiroUtils;

import java.util.List;

/**
 * Created by ganguixiang on 2017/10/16.
 */
public class CommonController {

    public ShiroUser getCurrentUser() {
        return ShiroUtils.getCurrentUser();
    }

    public String getCurrentUsername() {
        return ShiroUtils.getCurrentUsername();
    }

    public List<String> getCurrentUserRoles() {
        return ShiroUtils.getCurrentUserRoles();
    }

    public List<String> getCurrentUserRoleIds() {
        return ShiroUtils.getCurrentUserRoleIds();
    }
}
