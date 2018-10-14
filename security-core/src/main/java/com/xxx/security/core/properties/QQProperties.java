package com.xxx.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @description: 第三方接入-QQ 登录 配置属性
 * 继承SocialProperties类 拥有字段 appId  appSecret
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQProperties extends SocialProperties {

    /**
     * 第三方服务提供商ID
     */
    private String providerId = "qq";


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
