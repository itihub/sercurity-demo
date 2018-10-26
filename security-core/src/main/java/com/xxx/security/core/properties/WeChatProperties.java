package com.xxx.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Description: 第三方接入-微信 配置属性类
 * @Author: JiZhe
 * @CreateDate: 2018/10/13 15:53
 */
public class WeChatProperties extends SocialProperties {

    /**
     * 第三放服务商唯一标识
     * 用来解决发起第三方登陆的url 默认wechat
     */
    private String providerId = "wechat";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
