package com.ggxspace.autumn.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限Controller
 * Created by ganguixiang on 2017/9/28.
 */
@Controller
@RequestMapping("permission")
public class PermissionController {


    @RequestMapping("/")
    public String list() {
        return "permission/list";
    }


}
