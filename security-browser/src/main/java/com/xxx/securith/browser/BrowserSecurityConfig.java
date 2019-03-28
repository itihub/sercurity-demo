package com.xxx.securith.browser;

import com.xxx.security.core.authentication.AbstractChannelSecurityConfig;
import com.xxx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xxx.security.core.authorize.AuthorizeConfigManager;
import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @description: 浏览器自定义安全配置
 * @author: Administrator
 * @date: 2018/10/15 0015
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    //读取配置文件
    @Autowired
    private SecurityProperties securityProperties;

    //引入数据源
    @Autowired
    private DataSource dataSource;

    //处理验证码过滤器配置
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    //短信验证安全配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 社交配置 即第三方登录介入配置
     * 引入SpringSocialConfigurer
     * @see com.xxx.security.core.social.SocialConfig
     */
    @Autowired
    private SpringSocialConfigurer xxxSpringSocialConfigurer;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * session失效策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    /**
     *session过期策略（比如：同一账号 另一处登陆）
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * 退出成功处理流程
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     *权限配置管理器
     */
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //配置认证口令
        applyPasswordAuthenticationConfig(http, securityProperties.browser.getSingInSuccessUrl());

        http
                //添加验证码安全配置
                .apply(validateCodeSecurityConfig)
                    .and()
                //添加短信安全配置
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                //添加 spring social配置
                .apply(xxxSpringSocialConfigurer)

                    .and()
                //配置记住我功能
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                //记住我 过期时间配置
                .tokenValiditySeconds(securityProperties.browser.getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                    .and()
                //session管理
                .sessionManagement()
                //session失效跳转url
                    .invalidSessionStrategy(invalidSessionStrategy)
                    //同一个用户只能有一个session会话
                    .maximumSessions(securityProperties.browser.getSession().getMaximumSessions())
                    //当session超过最大限制数量阻止登陆操作 （关闭，会自动剔除前登陆session）
                    .maxSessionsPreventsLogin((securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin()))
                    //session过期的会话策略
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                .logout()
                    //自定义登出访问url 默认"logout"
                    .logoutUrl("/signout")
                    //登出成功跳转url
//                    .logoutSuccessUrl(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
                    //自定义登出成功处理流程  不能与logoutSuccessUrl共同配置 两者互斥
                    .logoutSuccessHandler(logoutSuccessHandler)
                    //删除指定cookie
                    .deleteCookies("JSESSIONID")
                    .and()
//                //授权请求配置
//                .authorizeRequests()
//                //配置无需身份验证url
//                .antMatchers(
//                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL
//                        , SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE
//                        , SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
//                        , securityProperties.browser.getLoginPage()
//                        , securityProperties.browser.getSignUpUrl()
//                        , SecurityConstants.DEFAULT_SESSION_INVALID_URL
//                        ,"/user/register").permitAll()
//                //任何请求都需要身份认证
//                .anyRequest().authenticated()
//                    .and()
                //关闭跨站请求防护
                .csrf().disable();

                //权限授权配置
                authorizeConfigManager.config(http.authorizeRequests());

    }


    /**
     * 记住我功能
     * @return jdbc
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // TODO: 首次使用记住我功能将一下注释打开 开启项目启动自动创建记住我功能数据表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
