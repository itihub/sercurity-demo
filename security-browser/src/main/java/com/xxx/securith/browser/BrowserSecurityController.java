package com.xxx.securith.browser;

import com.xxx.securith.browser.support.SimpleResponse;
import com.xxx.securith.browser.support.SocialUserInfo;
import com.xxx.security.core.properties.SecurityConstants;
import com.xxx.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

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

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 引入自定义配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     *
     */
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws IOException {

        //拿到引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            //获取请求的URL
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是：{}", redirectUrl);
            //判断此前请求的url是否是html请求
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                //跳转请求
                redirectStrategy.sendRedirect(request, response
                        , securityProperties.browser.getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

    /**
     * 获取社交用户信息
     * @return 社交用户信息
     */
    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        SocialUserInfo userInfo = new SocialUserInfo();
        //使用工具类获取session中用户登录信息
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        //转换对象
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());

        return userInfo;
    }

    /**
     * session失效处理
     * @return
     */
    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse sessionInvalid(){
        String message = "session失效";
        return new SimpleResponse(message);
    }

}
