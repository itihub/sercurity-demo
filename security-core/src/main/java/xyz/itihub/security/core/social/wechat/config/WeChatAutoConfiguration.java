package xyz.itihub.security.core.social.wechat.config;

import xyz.itihub.security.core.properties.SecurityProperties;
import xyz.itihub.security.core.properties.WeChatProperties;
import xyz.itihub.security.core.social.XxxConnectView;
import xyz.itihub.security.core.social.wechat.connet.WeChatConnectionFactory;
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
public class WeChatAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeChatProperties wechatConfig = securityProperties.getSocial().getWechat();
        return new WeChatConnectionFactory(wechatConfig.getProviderId(), wechatConfig.getAppId(),
                wechatConfig.getAppSecret());
    }

    /**
     * connect/wechatConnected 绑定视图
     * connect/wechatConnect 解绑视图
     * @return
     */
    @Bean({"connect/wechatConnect", "connect/wechatConnected"})
    @ConditionalOnMissingBean(name = "wechatConnectedView")     //支持覆盖开发  注入一个beanname wechatConnectedView 即可覆盖
    public View wechatConnectedView() {
        return new XxxConnectView();
    }
}
