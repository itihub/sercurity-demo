package com.xxx.security.core.authentication;

import com.xxx.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @description: 抽象通道安全配置
 * @author: Administrator
 * @date: 2018/10/15 0015
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 引入自定义成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler baseAuthenticationSuccessHandle;

    /**
     * 引入自定义错误处理器
     */
    @Autowired
    private AuthenticationFailureHandler baseAuthenticationFailureHandle;


    /**
     * 应用口令认证配置
     * @param http
     * @throws Exception
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {

        //默认httpBasic认证
//       http.httpBasic()

        http.formLogin()
                //默认登陆页
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登陆处理url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //配置自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //配置自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle);
    }
}
