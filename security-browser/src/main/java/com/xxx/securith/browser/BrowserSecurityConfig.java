package com.xxx.securith.browser;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Description:    自定义安全配置适配器
 * @Author: JiZhe
 * @CreateDate: 2018/8/26 15:50
 */
@Component
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //默认httpBasic认证
//        http.httpBasic()

        //form表单请求认证
        http.formLogin().and()
                .authorizeRequests()    //请求授权
                .anyRequest()   //请求方式
                .authenticated();    //任何请求都需要身份认证

    }
}
