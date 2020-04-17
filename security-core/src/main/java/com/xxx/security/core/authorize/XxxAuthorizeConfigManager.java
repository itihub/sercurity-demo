package com.xxx.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 权限配置管理器
 * @author: Administrator
 * @date: 2018/10/29 0029
 */
@Component
public class XxxAuthorizeConfigManager implements AuthorizeConfigManager {

    /**
     * 将所有实现 AuthorizeConfigProvider配置提供器 收集起来
     */
    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //便历所有配置并应用
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }

        // TODO: 如需引用动态权限认证需要注掉一下代码
        // TODO: 如卸载动态权限认证需要打开一下代码          （以下代码含义：任何请求都需要权限认证）
        //除了配置的 所有请求都需要授权
        config.anyRequest().authenticated(); //rbac权限引入需要注掉这行代码

    }
}
