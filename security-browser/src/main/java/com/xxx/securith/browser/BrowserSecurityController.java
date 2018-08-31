package com.xxx.securith.browser;

import com.xxx.securith.browser.support.SimpleResponse;
import com.xxx.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 处理不同请求的登录
 * @author: Administrator
 * @date: 2018/08/27 0027
 */
@Slf4j
@RestController
public class BrowserSecurityController {

    /**
     * 请求缓存
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 引入自定义配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 身份认证 跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/request")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws IOException {

        //拿到引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            //获取请求的URL
            String redirectUrl = savedRequest.getRedirectUrl();

            //判断此前请求的url是否是html请求
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                //跳转请求
                redirectStrategy.sendRedirect(request, response
                        , securityProperties.browser.getLoginPage());
            }
        }

        return new SimpleResponse("权限不足，请进行身份验证");
    }

}
