package com.xxx.securith.app.social;

import com.xxx.securith.app.social.openid.OpenIdAuthenticationFilter;
import com.xxx.securith.app.social.openid.OpenIdAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @description: 将自定义认证流程配置到Security
 * @author: Administrator
 * @date: 2018/10/24 0024
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler baseAuthenticationSuccessHandle;

    @Autowired
    private AuthenticationFailureHandler baseAuthenticationFailureHandle;


    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;



    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //构建自定义认证过滤器
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();

        openIdAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(baseAuthenticationSuccessHandle);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(baseAuthenticationFailureHandle);

        //构建自定义认证逻辑类
        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        //进行配置以及过滤器添加
        builder.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
