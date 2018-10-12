package com.xxx.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @description: 个性化SpringSocial配置
 * @author: Administrator
 * @date: 2018/10/12 0012
 */
public class XxxSpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * Spring Social 拦截URL
     */
    private String filterProcessesUrl;

    /**
     * 构造器
     * @param filterProcessesUrl
     */
    public XxxSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        //个性化拦截url  默认拦截url："/auth"
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return super.postProcess(object);
    }
}
