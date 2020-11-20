package xyz.itihub.demo.config.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Description: 时间过滤器
 * @Author: JiZhe
 * @CreateDate: 2018/8/25 21:02
 */
@Slf4j
public class TimeFilter implements Filter {

    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TimeFilter initialized");
    }

    /**
     * 过滤处理
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("filter start time {}", LocalDateTime.now());
        chain.doFilter(request, response);
        log.info("filter end time {}", LocalDateTime.now());
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
