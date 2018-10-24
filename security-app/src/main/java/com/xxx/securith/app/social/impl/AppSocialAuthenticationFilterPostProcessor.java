package com.xxx.securith.app.social.impl;

import com.xxx.security.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @description: app与浏览器不同，不能使用默认的成功处理器
 * @author: Administrator
 * @date: 2018/10/23 0023
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {

    /**
     * 注入自定义app成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler baseAuthenticationSuccessHandle;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        //将自定义成功处理加入到过滤器中
        socialAuthenticationFilter.setAuthenticationSuccessHandler(baseAuthenticationSuccessHandle);
    }
}
