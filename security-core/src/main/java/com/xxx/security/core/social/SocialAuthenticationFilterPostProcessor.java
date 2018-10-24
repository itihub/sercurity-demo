package com.xxx.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @description: 社交认证过滤器后置处理器流程扩展接口（主要用于app登录环境）
 * @author: Administrator
 * @date: 2018/10/23 0023
 */
public interface SocialAuthenticationFilterPostProcessor {

    /**
     * 处理流程
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
