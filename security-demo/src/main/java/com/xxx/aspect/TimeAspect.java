package com.xxx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Description:    自定义切片类
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 21:51
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around(value = "execution(* com.xxx.controller.*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        // 获取请求方法的入参
        Object[] args = pjp.getArgs();
        for (Object obj : args) {
            log.info("request method param :{}",obj);
        }

        //获取请求方法的返回结果
        Object proceed = pjp.proceed();

        return proceed;
    }

}
