package com.xxx.securith.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 抽象session会话策略
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
@Slf4j
public class AbstractSessionStrategy {

    /**
     * 跳转目标URl
     */
    private String destinationUrl;

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造器
     * @param invalidSessionUrl 跳转目标URl
     */
    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    /**
     * 无效会话处理
     * @param request
     * @param response
     * @throws IOException
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }

        // 获取请求uri
        String sourceUrl = request.getRequestURI();

        // 判断请求是否包含html后缀
        if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            String targetUrl = destinationUrl;
            log.info("session失效,跳转到 {}", targetUrl);
            //html 重定向响应
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }else{
            //不包含
            String message = "session已失效%s";
            if(isConcurrency()){
                message = String.format(message, "，有可能是并发登录导致的");
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(message)));
        }

    }

    /**
     * session失效是否是并发导致的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    /**
     * 创建一个新的session回话
     * @param createNewSession 判断标识
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
