package com.xxx.security.core.social;

import com.xxx.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @description: Social 配置类
 * @author: Administrator
 * @date: 2018/10/08 0008
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    /**
     * 注册数据源
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * ConnectionSignUp 第三方登陆新用户自动注册实现接口
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /**
         * 初次使用请执行此sql脚本
         * @see org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository.sql
         */
        // TODO: 2018/10/08 0008 数据库表名个性化前缀 释放此注释
//        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setTablePrefix("");

        /**
         * @param dataSource 数据源
         * @param connectionFactoryLocator 连接工厂
         * @param textEncryptor 数据加解密方式
         */
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //判断 是否有实现 ConnectionSignUp 接口
        if (connectionSignUp != null){
            //有实现 加入
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * SpringSocialConfigurer 配置
     * @return
     */
    @Bean
    public SpringSocialConfigurer xxxSpringSocialConfigurer() {
        String filterProcessesUrl = securityProperties.social.getFilterProcessesUrl();
        XxxSpringSocialConfigurer configurer = new XxxSpringSocialConfigurer(filterProcessesUrl);
        //自定义注册地址
        configurer.signupUrl(securityProperties.browser.getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }


    /**
     * Spring social 工具类
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
