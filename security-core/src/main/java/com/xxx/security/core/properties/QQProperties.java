package com.xxx.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @description: 第三方介入-QQ 登录 配置属性
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
public class QQProperties extends SocialProperties {

    /**
     * 第三方提供时ID
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
