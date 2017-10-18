package com.ggxspace.autumn.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggxspace.autumn.vo.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义认证过滤器
 * 分别处理ajax请求和非ajax请求
 * Created by ganguixiang on 2017/10/16.
 */
public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 重写onLoginSuccess，处理登陆成功的逻辑
     * 1.ajax请求，返回json，提示用户登录成功
     * 2.不是ajava请求，执行重定向
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if ("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
            // Ajax请求，响应请求,返回json字符串
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();

            Result result = new Result.Builder().message("登陆成功").build();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);

            out.println(json);

            out.flush();
            out.close();
            return false;
        } else {
            // 不是ajax请求
            return super.onLoginSuccess(token, subject, httpServletRequest, httpServletResponse);
        }

    }

    /**
     * 重写onLoginFailure方法，处理登陆失败逻辑
     * 1.非ajax请求，使用shiro原来的处理方式
     * 2.ajax请求，返回json，提示用户名/密码错误
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
            // ajax请求
            // 响应请求,返回json字符串
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                PrintWriter out = response.getWriter();

                Result result = new Result.Builder().code(Result.FAIL).message("用户名/密码错误").build();

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);

                out.println(json);

                out.flush();
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        } else {
            // 非ajax请求，使用默认处理方式
            return super.onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 重写onAccessDenied，自定义处理ajax请求
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 若为AJAX请求且不是登陆请求,返回JSON
        if ("XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With")) && !isLoginSubmission(request, response)) {

            // 响应请求,返回json字符串
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpResponse.getWriter();

            Result result = new Result.Builder().code(Result.LOGIN_TIMEOUT).message("登录超时").build();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);

            out.println(json);

            out.flush();
            out.close();
            return false;

        } else {
            // 非ajax请求调用默认处理方式
            return super.onAccessDenied(request, response);
        }

    }

    /**
     * 重写isAccessAllowed，处理ajax请求
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 登录请求直接返回true
        if (isLoginRequest(request, response)) {
            return true;
        }
        // 若为AJAX请求且不是登陆请求,返回JSON
        if ("XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"))) {

            try {
                // 响应请求,返回json字符串
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                PrintWriter out = httpResponse.getWriter();

                Result result = new Result.Builder().code(Result.NO_PERMISSION).message("没有权限").build();

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(result);

                out.println(json);

                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            // 非ajax请求处理方式
            return getSubject(request, response).isAuthenticated();
        }


//        return super.isAccessAllowed(request, response, mappedValue);
    }
}
