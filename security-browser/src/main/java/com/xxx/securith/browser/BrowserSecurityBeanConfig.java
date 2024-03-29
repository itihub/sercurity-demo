package com.xxx.securith.browser;

import com.xxx.securith.browser.logout.XxxLogoutSuccessHandler;
import com.xxx.securith.browser.session.CustomizeExpiredSessionStrategy;
import com.xxx.securith.browser.session.CustomizeInvalidSessionStrategy;
import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @description: 浏览器安全性配置
 * @author: Administrator
 * @date: 2018/10/19 0019
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 支持覆盖处理默认session失效
     * 实现 @see org.springframework.security.web.session.InvalidSessionStrategy 接口即可
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomizeInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 支持覆盖处理默认session过期
     * 实现 @see org.springframework.security.web.session.SessionInformationExpiredStrategy 接口
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomizeExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 支持覆盖处理登出成功处理程序
     * 实现 LogoutSuccessHandler接口即可
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new XxxLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }
}
