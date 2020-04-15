package com.xxx.security.core.authorize;

import com.xxx.security.core.properties.SecurityConstants;
import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @description: 默认授权表单提供者
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
@Component
@Order(Integer.MIN_VALUE)   //加载优先级
public class XxxAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        //以下路径无需授权
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL
                , SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE
                , SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID
                , SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
                , "/swagger-ui.html"
                , "/v2/api-docs"
                , "/swagger-resources/*"
                , securityProperties.browser.getLoginPage()
                , securityProperties.browser.getSignUpUrl()
                , securityProperties.browser.getUnauthorized()
                , SecurityConstants.DEFAULT_SESSION_INVALID_URL
                , securityProperties.browser.getSignOutUrl())
                .permitAll();

    }
}
