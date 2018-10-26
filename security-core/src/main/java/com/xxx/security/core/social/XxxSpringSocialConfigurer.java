package com.xxx.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @description: SpringSocial配置
 * @author: Administrator
 * @date: 2018/10/12 0012
 */
public class XxxSpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * Spring Social 拦截URL
     */
    private String filterProcessesUrl;

    /**
     * 自定义社交认证过滤器后置处理器
     */
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

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

        //判断自定义后置处理器
        if (socialAuthenticationFilterPostProcessor != null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }

        return super.postProcess(object);
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
