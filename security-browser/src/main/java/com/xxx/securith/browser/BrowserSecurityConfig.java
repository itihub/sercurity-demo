package com.xxx.securith.browser;

import com.xxx.securith.browser.authentication.BaseAuthenticationFailureHandle;
import com.xxx.securith.browser.authentication.BaseAuthenticationSuccessHandle;
import com.xxx.security.core.filter.ValidateCodeFilter;
import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description:    自定义安全配置适配器
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


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(baseAuthenticationFailureHandle);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        //默认httpBasic认证
//        http.httpBasic()

        //form表单请求认证
        http.formLogin()
                .loginPage("/authentication/request")
                .loginProcessingUrl("/authentication/form")
                //使用自定义成功处理器
                .successHandler(baseAuthenticationSuccessHandle)
                //使用自定义失败处理器
                .failureHandler(baseAuthenticationFailureHandle)
                .and()
                .authorizeRequests()    //请求授权
                //匹配此页面无需身份验证
                .antMatchers("/authentication/request", "/code/image"
                        , securityProperties.browser.getLoginPage()).permitAll()
                .anyRequest()   //请求方式
                .authenticated()    //任何请求都需要身份认证
                .and().csrf().disable();    //关闭跨站请求防护
    }
}
