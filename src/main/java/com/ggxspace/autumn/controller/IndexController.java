package com.ggxspace.autumn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.awt.SunHints;

/**
 * 首页Controller
 * Created by ganguixiang on 2017/9/28.
 */
@Controller
public class IndexController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
