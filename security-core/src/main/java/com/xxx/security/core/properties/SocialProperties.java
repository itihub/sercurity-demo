package com.xxx.security.core.properties;

/**
 * @description: Social 配置属性
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class SocialProperties {

    /**
     * Spring social 过滤统一资源定位器 默认 auth
     */
    private String filterProcessesUrl = "auth";

    /**
     * qq配置
     */
    private QQProperties qq = new QQProperties();

    /**
     * 微信配置属性
     */
    private WechatProperties wechat = new WechatProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WechatProperties getWechat() {
        return wechat;
    }

    public void setWechat(WechatProperties wechat) {
        this.wechat = wechat;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
