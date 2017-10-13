package com.ggxspace.autumn.controller;

import com.ggxspace.autumn.exception.AutumnException;
import com.ggxspace.autumn.util.ObjectUtils;
import com.ggxspace.autumn.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ganguixiang on 2017/10/13.
 */
@RestController
@RequestMapping(value = "login")
public class LoginController {

    /**
     * 跳转到登陆页
     * @return
     */
    @GetMapping
    public ModelAndView goLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            return new ModelAndView("index");
        }
        return new ModelAndView("login");
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @PostMapping
    public Result login(String username, String password) {

        ObjectUtils.requireNonNull(username);
        ObjectUtils.requireNonNull(password);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            // 如果当前用户没有认证过
            if (!currentUser.isAuthenticated()) {
                // 进行认证
                currentUser.login(token);
            }

        } catch (AuthenticationException e) {
            // 认证失败
            e.printStackTrace();
            System.out.print(e.getMessage());
            throw new AutumnException("用户名或密码错误");
        }
        return Result.ok();

    }
}
