package com.xxx.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 自定义拦截器
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 21:20
 */
@Slf4j
@Component
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * 前置拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setAttribute("startTime", System.currentTimeMillis());

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String className = handlerMethod.getBean().getClass().getName();
        String methodName = handlerMethod.getMethod().getName();

        log.info("request clss:{} method:{}", className, methodName);

        //放行所有拦截
        return true;
    }

    /**
     * 后置拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response
            , Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 异常后置拦截（无论如何都会调用）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response
            , Object handler, Exception ex) throws Exception {

        long endTime = System.currentTimeMillis();

        long startTime = (long) request.getAttribute("startTime");

        log.info("this is request time consuming {}", endTime - startTime);
        log.info("exception is {}", ex);
    }
}
