package com.xxx.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @description: 权限配置管理
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
public interface AuthorizeConfigManager {

    /**
     * 权限管理
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
