package com.xxx.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @Description: 自定义过滤器
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 21:02
 */
@Slf4j
public class TimeFilter implements Filter {

    /**
     * 初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 过滤处理
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("filter start time {}",new Date());
        chain.doFilter(request, response);
        log.info("filter end time {}",new Date());
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
