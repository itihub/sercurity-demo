package xyz.itihub.security.core.social.qq.config;

import xyz.itihub.security.core.properties.QQProperties;
import xyz.itihub.security.core.properties.SecurityProperties;
import xyz.itihub.security.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @description: QQ连接工厂配置
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
@Configuration
@ConditionalOnProperty(prefix = "xxx.security.social.qq", name = "app-id")      //限定注解  配置文件篇配置此配置类才起作用
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
