package com.ggxspace.autumn.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限Controller
 * Created by ganguixiang on 2017/9/28.
 */
//@Controller
//@RequestMapping("permission")
public class PermissionController {


    /**
     * 跳转列表页
     * @return
     */
    @RequestMapping("/")
    public String goList() {
        return "permission/list";
    }


}
