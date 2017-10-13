package com.ggxspace.autumn.aspect;

import com.ggxspace.autumn.exception.AutumnException;
import com.ggxspace.autumn.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Created by ganguixiang on 2017/9/29.
 */
@Aspect
@Component
public class ControllerLogAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);

    /**
     * 切点
     */
    @Pointcut("execution(public com.ggxspace.autumn.vo.Result com.ggxspace.autumn.controller..*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {

        // 开始时间
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // url
        LOGGER.info("url={}", request.getRequestURL());

        // method
        LOGGER.info("method={}", request.getMethod());

        // ip
        LOGGER.info("ip={}", request.getRemoteAddr());

        // 类方法
        LOGGER.info("class_method={}", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());

        // 参数
        LOGGER.info("args={}", pjp.getArgs());

        Result<?> result = new Result<>();
        try {
            result = (Result<?>) pjp.proceed();
        } catch (Throwable e) {
            result = handleException(pjp, e);
        }

        // 结果
        LOGGER.info("result = {}", result);

        // 处理请求所需时间
        LOGGER.info("cost time={}ms", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 处理异常
     * 可以处理已知异常和未知异常，未知异常可以做其它操作，比如发邮件等
     * @param pjp
     * @param e
     * @return
     */
    private Result<?> handleException(ProceedingJoinPoint pjp, Throwable e) {
        Result<?> result = new Result<>();
        LOGGER.error("{}", e);
        result.setCode(Result.FAIL);
        String message = "";
        // 已知异常
        if (e instanceof AutumnException) {
            message = e.getMessage();
        } else {
            // hibernate validatetor 校验异常
            Throwable causor = e.getCause();
            while (null != causor) {
                if (causor instanceof ConstraintViolationException) {
                    // 获取校验错误提示信息
                    message = ((ConstraintViolationException) causor).getConstraintViolations().iterator().next().getMessage();
                    break;
                }
                causor = causor.getCause();
            }
            // 未知异常
            if (StringUtils.isEmpty(message)) {
                message = "未知错误，请联系管理员";
            }
        }
        result.setMessage(message);
        return result;
    }


}
