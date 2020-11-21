package xyz.itihub.security.core.authentication;

import xyz.itihub.security.core.properties.SecurityConstants;
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
    protected void applyPasswordAuthenticationConfig(HttpSecurity http, String successUrl) throws Exception {

        //默认httpBasic认证
//       http.httpBasic()

        http.formLogin()
                //需要身份认证资源跳转到的URI
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登陆处理接口
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //配置自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //配置自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle)
                //登陆成功跳转URL
                .defaultSuccessUrl(successUrl);
    }
}
