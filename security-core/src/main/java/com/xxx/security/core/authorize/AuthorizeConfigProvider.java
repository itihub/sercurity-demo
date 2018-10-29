package com.xxx.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @description: 授权配置接口
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
public interface AuthorizeConfigProvider {

    /**
     * 权限配置
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
