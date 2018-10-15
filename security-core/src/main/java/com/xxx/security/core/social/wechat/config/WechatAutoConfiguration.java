package com.xxx.security.core.social.wechat.config;

import com.xxx.security.core.properties.SecurityProperties;
import com.xxx.security.core.properties.WechatProperties;
import com.xxx.security.core.social.XxxConnectView;
import com.xxx.security.core.social.wechat.connet.WechatConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @Description:微信登录配置
 * @Author: JiZhe
 * @CreateDate: 2018/10/14 11:22
 */
@Configuration
@ConditionalOnProperty(prefix = "xxx.security.social.wechat", name = "app-id")
public class WechatAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WechatProperties wechatConfig = securityProperties.getSocial().getWechat();
        return new WechatConnectionFactory(wechatConfig.getProviderId(), wechatConfig.getAppId(),
                wechatConfig.getAppSecret());
    }

    @Bean({"connect/wechatConnect", "connect/wechatConnected"})
    @ConditionalOnMissingBean(name = "wechatConnectedView")
    public View WechatConnectedView() {
        return new XxxConnectView();
    }
}
