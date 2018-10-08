package com.xxx.securith.browser;

import com.xxx.securith.browser.authentication.BaseAuthenticationFailureHandle;
import com.xxx.securith.browser.authentication.BaseAuthenticationSuccessHandle;
import com.xxx.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xxx.security.core.filter.ValidateCodeFilter;
import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


/**
 * @Description:    浏览器自定义安全配置适配器
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 15:50
 */
@Component
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密解密工具
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 读取配置文件
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 引入自定义成功处理器
     */
    @Autowired
    private BaseAuthenticationSuccessHandle baseAuthenticationSuccessHandle;

    /**
     * 引入自定义错误处理器
     */
    @Autowired
    private BaseAuthenticationFailureHandle baseAuthenticationFailureHandle;

    /**
     * 引入数据源
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    /**
     * 记住我功能
     * @return jdbc
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // TODO: 2018/09/02 0002 首次使用记住我功能将一下注释打开 开启项目启动自动创建记住我功能数据表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //实例化 自定义图形验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(baseAuthenticationFailureHandle);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        //默认httpBasic认证
//        http.httpBasic()

        //form表单请求认证
        //将自定义图形验证码过滤器放入最前面
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/request")
                .loginProcessingUrl("/authentication/form")
                //使用自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //使用自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle)
                //记住我配置
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                //记住我 过期时间设置
                .tokenValiditySeconds(securityProperties.browser.getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()    //请求授权
                //匹配此页面无需身份验证
                .antMatchers("/authentication/request"
                        , "/code/*"
                        , securityProperties.browser.getLoginPage()).permitAll()
                .anyRequest()   //请求方式
                .authenticated()    //任何请求都需要身份认证
                .and().csrf().disable()    //关闭跨站请求防护
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(springSocialConfigurer);
    }
}
