package com.xxx.security;

import com.xxx.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义权限配置
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        config.antMatchers("/user")
                .hasAnyRole("ADMIN");
    }
}
