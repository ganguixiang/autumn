package com.ggxspace.autumn.aspect;

import com.ggxspace.autumn.vo.Result;
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
        result.setMessage(e.toString());

        return result;
    }


}
