package com.xxx.securith.app;

import com.xxx.securith.app.social.OpenIdAuthenticationSecurityConfig;
import com.xxx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xxx.security.core.properties.SecurityConstants;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @description: 资源服务器
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
@Configuration
@EnableResourceServer
public class XxxResourceServerConfig extends ResourceServerConfigurerAdapter {

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

    //短信验证安全配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 社交配置 即第三方登录介入配置
     * 引入SpringSocialConfigurer
     * @see com.xxx.security.core.social.SocialConfig
     */
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    /**
     * 处理验证码过滤器配置
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * openId换取认证token
     */
    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    /**
     * 资源服务器配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                //默认登陆页
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登陆处理url
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //配置自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //配置自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle);
        http
                //添加验证码安全配置
                // TODO: 2018/10/23 0023  无需用户名密码登录 图形验证码请注掉以下两行代码
//                .apply(validateCodeSecurityConfig)
//                .and()
                //添加短信安全配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //openId换取认证token
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                //添加 spring social配置
                .apply(springSocialConfigurer)
                .and()
                //授权请求配置
                .authorizeRequests()
                //配置无需身份验证url
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL
                        ,SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE
                        , SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
                        , securityProperties.browser.getLoginPage()
                        , securityProperties.browser.getSignUpUrl()
                        , SecurityConstants.DEFAULT_SESSION_INVALID_URL
                        ,"/user/register"
                        ,"/social/signUp").permitAll()
                //任何请求都需要身份认证
                .anyRequest().authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable();
    }
}
