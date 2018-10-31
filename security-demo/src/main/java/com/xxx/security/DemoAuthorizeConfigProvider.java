package com.xxx.security;

import com.xxx.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义权限配置
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
@Component
@Order(Integer.MAX_VALUE)   //加载优先级
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

//        config.antMatchers("/user")
//                .hasAnyRole("ADMIN");

        //动态权限表达式
        config.anyRequest().access("@rbacService.hasPermission(request, authentication)");
    }
}
